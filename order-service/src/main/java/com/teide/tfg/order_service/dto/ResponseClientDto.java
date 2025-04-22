package com.teide.tfg.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseClientDto {
    private UserDto  result;
    private String message;
    private int status;
}
