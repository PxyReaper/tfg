package com.tfg.msvc.product_service.controller.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusDto {
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Integer  status;
    private String message;
}
