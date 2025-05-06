package com.teide.tfg.order_service.factory;

import com.teide.tfg.order_service.dto.ResponseDto;

public class ResponseFactory {
    public static <T> ResponseDto<T> generateSuccesResponse(T response,String message,int status,Integer page,Integer size,Integer totalPages){
        return new ResponseDto<>(response,message,status,page,size,totalPages);
    }

    public static <T> ResponseDto<T> generateErrorResponse(String message, int status ){
        return new ResponseDto<>(null,message,status,null,null,null);
    }
 }
