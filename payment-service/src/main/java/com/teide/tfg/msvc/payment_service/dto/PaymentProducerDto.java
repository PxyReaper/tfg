package com.teide.tfg.msvc.payment_service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentProducerDto {
    private List<Long> productsIds;
    private String token;
}
