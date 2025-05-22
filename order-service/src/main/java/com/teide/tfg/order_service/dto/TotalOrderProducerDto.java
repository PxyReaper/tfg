package com.teide.tfg.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TotalOrderProducerDto {
    List<ObjectConsumerDto> products;
    Long id;
}
