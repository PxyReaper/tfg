package com.teide.tfg.msvc.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObjectProducerDto {
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
