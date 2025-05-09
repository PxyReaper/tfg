package com.tfg.msvc.product_service.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Nombre")
    private String name;

    @Column(name = "Descripcion")
    private String description;

    @Column(name = "Precio")
    private String price;
}
