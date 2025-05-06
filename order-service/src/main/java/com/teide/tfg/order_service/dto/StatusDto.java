package com.teide.tfg.order_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDto {
    private String message;
    private int status;
    private Integer page;
    private Integer size;
    private Integer totalPages;

}
