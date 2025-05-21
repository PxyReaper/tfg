package com.teide.tfg.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.model.OrderEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {
    public Page<OrderDto> findALl(int page, int size);
    public OrderDto findById(Long id);
    public OrderDto  findByMail(String mail);
    public void save(String paymentProducer) throws JsonProcessingException;
}
