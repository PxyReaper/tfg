package com.teide.tfg.order_service.service;

import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.model.OrderEntity;

import java.util.List;

public interface IOrderService {
    public List<OrderDto> findALl();
    public OrderDto findById();
    public OrderDto  findByMail();
}
