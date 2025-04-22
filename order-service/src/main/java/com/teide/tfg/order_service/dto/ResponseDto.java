package com.teide.tfg.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class ResponseDto <T> extends StatusDto{
    private T result;
    public ResponseDto(T result , String message, int status){
        super(message,status);
        this.result = result;

    }
}
