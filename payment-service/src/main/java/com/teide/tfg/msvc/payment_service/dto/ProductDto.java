package com.teide.tfg.msvc.payment_service.dto;

import lombok.*;

import java.math.BigDecimal;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
}
