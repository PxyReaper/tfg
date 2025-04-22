package com.teide.tfg.order_service.service.impl;

import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.repository.OrderRepository;
import com.teide.tfg.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    @Override
    public List<OrderDto> findALl() {
        return  orderRepository.findOrdersWithMail();
    }

    @Override
    public OrderDto findById(Long id) {
        return orderRepository.findOrderWithMailById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public OrderDto findByMail(String mail) {
        return orderRepository.findOrderWithMailByMail(mail).orElseThrow(RuntimeException::new);

    }
}
