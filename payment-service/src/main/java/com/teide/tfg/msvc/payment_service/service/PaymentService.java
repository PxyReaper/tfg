package com.teide.tfg.msvc.payment_service.service;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import com.teide.tfg.msvc.payment_service.dto.Cart;
import com.teide.tfg.msvc.payment_service.dto.Example;
import com.teide.tfg.msvc.payment_service.model.CompletedOrder;
import com.teide.tfg.msvc.payment_service.model.PaymentOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PayPalHttpClient payPalHttpClient;
    public PaymentOrder createOrder(Example carrito){
        OrderRequest request = new OrderRequest();
        request.checkoutPaymentIntent("CAPTURE");
        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown()
                .currencyCode("EUR").value(carrito.getAmount().toString());
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountWithBreakdown);
        request.purchaseUnits(List.of(purchaseUnitRequest));
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("http://localhost:8900/capture")
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
                return new CompletedOrder("success", token);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new CompletedOrder("error");
    }


}
