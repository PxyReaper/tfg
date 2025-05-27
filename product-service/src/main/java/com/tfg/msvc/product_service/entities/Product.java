package com.tfg.msvc.product_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private long id;
    @Column(name = "nombre")
    private String name;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "precio_unit")
    private BigDecimal price;
    private String portada;
    @Column(name = "lateral_izquierdo")
    private String lateralIzquierdo;
    @Column(name = "lateral_derecho")
    private String lateralDerecho;
    @Column(name = "arriba")
    private String arriba;
}
