package com.teide.tfg.order_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teide.tfg.order_service.MessageExample;
import com.teide.tfg.order_service.clients.AuthClient;
import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.dto.PaymentConsumer;
import com.teide.tfg.order_service.dto.UserDto;
import com.teide.tfg.order_service.exception.OrderNotFoundByIdException;
import com.teide.tfg.order_service.model.OrderEntity;
import com.teide.tfg.order_service.repository.OrderRepository;
import com.teide.tfg.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.joda.time.format.DateTimeFormat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final AuthClient authClient;
    @Override
    public Page<OrderDto> findALl(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return this.orderRepository.findOrdersWithMail(pageable);
    }

    @Override
    public OrderDto findById(Long id) {
        Optional<OrderDto> order =  orderRepository.findOrderWithMailById(id);
        order.orElseThrow(() -> new OrderNotFoundByIdException("Producto no encontrado por el id proporcionado"));
        return  order.get();
    }

    @Override
    public OrderDto findByMail(String mail) {
        Optional<OrderDto> order =  orderRepository.findOrderWithMailByMail(mail);
        order.orElseThrow(() -> new OrderNotFoundByIdException("Producto no encontrado por el mail proporcionado"));
        return  order.get();

    }
    @KafkaListener(topics = "payment-topic",groupId = "paymentId")
    @Override
    public void save(String paymentProducer) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PaymentConsumer consumer = mapper.readValue(paymentProducer, PaymentConsumer.class);
        System.out.println(consumer);
        String token =consumer.getToken();
        UserDto user = authClient.getCurrentUser(token);
        LocalDate now = LocalDate.from(LocalDateTime.now());
        OrderEntity order = new OrderEntity(null,user.getIdUsuario(), now,now.plusWeeks(1L),consumer.getAmount());
        orderRepository.save(order);
    }


}
