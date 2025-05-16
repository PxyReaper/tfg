package com.tfg.msvc.product_service.factory;

import com.tfg.msvc.product_service.controller.DTO.ResponseDto;

public class ResponseFactory {
    public static <T> ResponseDto<T> generateSuccesResponse(T result, String message, Integer status, Integer page, Integer size,
                                                            Integer totalPages) {
        return new ResponseDto<>(result, status, message, page, size, totalPages);
    }
}
