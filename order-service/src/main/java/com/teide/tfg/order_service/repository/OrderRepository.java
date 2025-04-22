package com.teide.tfg.order_service.repository;

import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    @Query(
            value = "SELECT o.id_encabezado AS id, u.email AS mail, o.fecha_compra AS fechaCompra, o.fecha_entrega AS fechaEntrega " +
                    "FROM producto_h o " +
                    "JOIN usuarios u ON o.id_usuario = u.id_usuario",
            nativeQuery = true)
    List<OrderDto> findOrdersWithMail();
    @Query(
            value = "SELECT o.id_encabezado AS id, u.email AS mail, o.fecha_compra AS fechaCompra, o.fecha_entrega AS fechaEntrega " +
                    "FROM producto_h o " +
                    "JOIN usuarios u ON o.id_usuario = u.id_usuario " +
                    "where o.id_encabezado = :id",
            nativeQuery = true)


    Optional<OrderDto> findOrderWithMailById(@Param("id") Long id);

    @Query(
            value = "SELECT o.id_encabezado AS id, u.email AS mail, o.fecha_compra AS fechaCompra, o.fecha_entrega AS fechaEntrega " +
                    "FROM producto_h o " +
                    "JOIN usuarios u ON o.id_usuario = u.id_usuario " +
                    "where u.email = :mail",
            nativeQuery = true)
    Optional<OrderDto> findOrderWithMailByMail(@Param("mail") String mail);

    public OrderEntity findOrderEntityByIdUsuario(String idUsuario);
}
