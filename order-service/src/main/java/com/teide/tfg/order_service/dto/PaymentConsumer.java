package com.teide.tfg.order_service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentConsumer {
    private List<Long> productsIds;
    private String token;
    private BigDecimal amount;
}
