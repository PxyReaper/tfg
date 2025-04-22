package com.teide.tfg.order_service.repository;

import com.teide.tfg.order_service.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {
    public OrderEntity findOrderEntityByIdUsuario(String idUsuario);
}
