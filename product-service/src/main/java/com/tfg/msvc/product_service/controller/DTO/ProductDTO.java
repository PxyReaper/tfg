package com.tfg.msvc.product_service.controller.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ProductDTO {

    private long id;

    private String name;

    private String description;

    private BigDecimal price;
}
