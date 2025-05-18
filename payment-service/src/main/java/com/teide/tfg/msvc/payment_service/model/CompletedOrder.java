package com.teide.tfg.msvc.payment_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CompletedOrder {
    private String status;
    private String payId;
    public CompletedOrder(String status){
        this.status = status;
    }

}
