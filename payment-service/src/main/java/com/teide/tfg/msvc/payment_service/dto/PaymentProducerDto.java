package com.teide.tfg.msvc.payment_service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentProducerDto {
   private List<ObjectProducerDto> productsId;
    private String token;
    private BigDecimal amount;
}
