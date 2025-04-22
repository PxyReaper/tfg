package com.teide.tfg.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseClientDto extends  StatusDto{
    private UserDto  result;
    public ResponseClientDto(UserDto result, String message, int status){
        super(message,status);
        this.result = result;
    }

}
