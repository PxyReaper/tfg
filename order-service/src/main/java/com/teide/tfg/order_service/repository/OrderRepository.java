package com.teide.tfg.order_service.repository;

import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.model.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    @Query(nativeQuery = true)
    Page<OrderDto> findOrdersWithMail(Pageable pageable);
    @Query(nativeQuery = true)


    Optional<OrderDto> findOrderWithMailById(@Param("id") Long id);

    @Query(nativeQuery = true)
    Optional<OrderDto> findOrderWithMailByMail(@Param("mail") String mail);

    public OrderEntity findOrderEntityByIdUsuario(String idUsuario);
}
