package com.teide.tfg.msvc.payment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cantidad_producto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductQuantityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_cantidad")
    private Long idProductQuantity;
    private Long productId;
    private int quantity;
}
