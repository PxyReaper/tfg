package com.teide.tfg.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Table(name = "producto_h")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@NamedNativeQueries(
        {
                @NamedNativeQuery(name="OrderEntity.findOrdersWithMail",query = "SELECT o.id_encabezado AS id, u.email AS mail, " +
                        "o.fecha_compra AS fechaCompra, o.fecha_entrega AS fechaEntrega " +
                        "FROM pedido_h o " +
                        "JOIN usuarios u ON o.id_usuario = u.id_usuario",resultSetMapping = "OrderDtoMapping"),
                @NamedNativeQuery(
                        name="OrderEntity.findOrderWithMailById",
                        query = "SELECT o.id_encabezado AS id, u.email AS mail, o.fecha_compra AS fechaCompra, o.fecha_entrega AS fechaEntrega " +
                                "FROM pedido_h o " +
                                "JOIN usuarios u ON o.id_usuario = u.id_usuario " +
                                "where o.id_encabezado = :id",
                        resultSetMapping = "OrderDtoMapping"
                ),
                @NamedNativeQuery(
                        name="OrderEntity.findOrderWithMailByMail",
                        query = "SELECT o.id_encabezado AS id, u.email AS mail, o.fecha_compra AS fechaCompra, o.fecha_entrega AS fechaEntrega " +
                                "FROM pedido_h o " +
                                "JOIN usuarios u ON o.id_usuario = u.id_usuario " +
                                "where u.email = :mail",
                        resultSetMapping = "OrderDtoMapping"
                )
        }
)
@SqlResultSetMapping(
        name = "OrderDtoMapping",
        classes = @ConstructorResult(
                targetClass = com.teide.tfg.order_service.dto.OrderDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "mail", type = String.class),
                        @ColumnResult(name = "fechaCompra", type = LocalDate.class),
                        @ColumnResult(name = "fechaEntrega", type = LocalDate.class)
                }
        )
)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_encabezado")
    private Long id;
    @Column(name = "id_usuario")
    private String idUsuario;
    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;
    @Column(name = "fecha_entrega")
    private LocalDate fecha_entrega;

}
