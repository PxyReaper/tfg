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
