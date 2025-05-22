package com.teide.tfg.msvc.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObjectProducerDto {
    private Long productId;
    private Integer quantity;
}
