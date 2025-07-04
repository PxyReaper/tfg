package com.teide.tfg.msvc.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantity {
    private ProductDto product;
    private int quantity;

}
