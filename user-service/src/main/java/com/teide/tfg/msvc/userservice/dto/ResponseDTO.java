package com.teide.tfg.msvc.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ResponseDTO<T>  extends  StatusDTO{
    T result;
    public ResponseDTO(T result, int status, String message) {
        super(status,message);
        this.result = result;

    }
}
