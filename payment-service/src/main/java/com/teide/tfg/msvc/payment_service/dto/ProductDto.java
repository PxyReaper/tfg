package com.teide.tfg.msvc.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter

@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precioUnit;
}
