package com.teide.tfg.order_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObjectConsumerDto {
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;

}
