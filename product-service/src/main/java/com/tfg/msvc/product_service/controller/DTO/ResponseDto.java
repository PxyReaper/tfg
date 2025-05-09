package com.tfg.msvc.product_service.controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T>extends  StatusDto {
    private T result;
    public ResponseDto(T result, Integer status, String message,Integer page,Integer size, Integer totalPages) {
        super(page,size,totalPages,status,message);
        this.result = result;
    }

}
