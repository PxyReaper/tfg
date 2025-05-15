package com.teide.tfg.order_service.service.impl;

import com.teide.tfg.order_service.MessageExample;
import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.exception.OrderNotFoundByIdException;
import com.teide.tfg.order_service.repository.OrderRepository;
import com.teide.tfg.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
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
    @KafkaListener(topics ="orders-topic",groupId = "products-id")
    public void mensaje(Map<String,Object> messageConsume){
        System.out.println("hola?");
        System.out.println(messageConsume.get("message"));
    }
}
