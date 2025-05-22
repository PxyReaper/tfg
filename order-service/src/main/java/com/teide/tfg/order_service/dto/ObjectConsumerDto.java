package com.teide.tfg.order_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ObjectConsumerDto {
    private Long productId;
    private Integer quantity;
}
