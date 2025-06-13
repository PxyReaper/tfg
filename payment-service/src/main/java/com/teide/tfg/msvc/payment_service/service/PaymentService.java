package com.teide.tfg.msvc.payment_service.service;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.teide.tfg.msvc.payment_service.client.ProductClient;
import com.teide.tfg.msvc.payment_service.dto.*;
import com.teide.tfg.msvc.payment_service.exception.ProductModifiedException;
import com.teide.tfg.msvc.payment_service.model.CartEntity;
import com.teide.tfg.msvc.payment_service.model.CompletedOrder;
import com.teide.tfg.msvc.payment_service.model.PaymentOrder;
import com.teide.tfg.msvc.payment_service.model.ProductQuantityEntity;
import com.teide.tfg.msvc.payment_service.producer.PaymentProducer;
import com.teide.tfg.msvc.payment_service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PayPalHttpClient payPalHttpClient;
    private final ProductClient productClient;
    private final CartRepository cartRepository;
    private final PaymentProducer paymentProducer;
    @Transactional
    public PaymentOrder createOrder(Cart carrito,String token){
        List<ProductDto> productDtos = carrito.getProductCart()
                .stream().map(ProductQuantity::getProduct).toList();

        boolean exist = productClient.checkExistence(productDtos).getResult();
        BigDecimal quantity = this.checkQuantity(carrito);
        if(!exist){
            throw new ProductModifiedException("Uno de los productos ha sido modificado");

        }
        OrderRequest request = new OrderRequest();
        request.checkoutPaymentIntent("CAPTURE");
        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown()
                .currencyCode("EUR").value(quantity.toString());
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountWithBreakdown);
        request.purchaseUnits(List.of(purchaseUnitRequest));
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("http://localhost:4200/capture")
                .cancelUrl("http://localhost:8900/cancel");
        request.applicationContext(applicationContext);
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest()
                .requestBody(request);

        try{
            HttpResponse<Order> orderHttpResponse =
                    payPalHttpClient.execute(ordersCreateRequest);
            Order order = orderHttpResponse.result();
            String redirectUrl = order.links().stream()
                    .filter(link -> "approve".equals(link.rel()
                    ))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new)
                    .href();
            persistCartOnDatabase(carrito,order.id(),quantity,token);
            return new PaymentOrder("success",order.id(),redirectUrl);

        }catch (IOException ex){
            log.error(ex.getMessage());
            return new PaymentOrder("ERROR");
        }
    }
    public CompletedOrder completePayment(String token) {
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                this.sentSuccesPaymentToKafka(token);
                return new CompletedOrder("success", token);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new CompletedOrder("error");
    }
    private BigDecimal checkQuantity(Cart carrito){
        BigDecimal acumulado = carrito.getProductCart().stream()
                .map(p -> p.getProduct().getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if(acumulado.compareTo(carrito.getTotalQuantity()) != 0){
            return acumulado;
        }
        return carrito.getTotalQuantity();
    }
    private void persistCartOnDatabase(Cart carrito,String payId,BigDecimal quantity,String token){
        Set<ProductQuantityEntity> productQuantities = carrito.getProductCart()
                .stream().map(p ->
                        new ProductQuantityEntity(null,p.getProduct().getId(),p.getQuantity())).collect(Collectors.toSet());
        CartEntity cartEntity = new CartEntity(payId,productQuantities,quantity,token);
        cartRepository.save(cartEntity);
    }
    private void sentSuccesPaymentToKafka(String id){
        Optional<CartEntity> cart = this.cartRepository.findById(id);
        List<ObjectProducerDto> ids = cart.get().getProduct().stream()
                .map( p -> {
                    ProductDto productDto = this.productClient.getProductById(p.getProductId()).getResult();
                    return new ObjectProducerDto(p.getProductId(),p.getQuantity(),productDto.getPrice());
                }).toList();
        PaymentProducerDto confirmPayment = new PaymentProducerDto(ids,cart.get().getUserToken(),cart.get().getPrice());
        System.out.println(confirmPayment);
        paymentProducer.sendPayment(confirmPayment);
    }
}

