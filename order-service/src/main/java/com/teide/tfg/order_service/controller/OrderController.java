package com.teide.tfg.order_service.controller;

import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.dto.ResponseDto;
import com.teide.tfg.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseDto<OrderDto>> findById(@PathVariable("id") Long id){
        OrderDto orderDto = orderService.findById(id);
        ResponseDto<OrderDto> response =  getResponseDtoByParameters(orderDto,"Pedido encontrado exitosamente", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ResponseDto<List<OrderDto>>> findALl(){
        List<OrderDto> orderDtos = orderService.findALl();
        ResponseDto<List<OrderDto>> response =getResponseDtoByParameters(orderDtos,null,200);
        return  ResponseEntity.ok(response);
    }

    private <T>ResponseDto<T> getResponseDtoByParameters(T orderDto,String message,int status){
        return new ResponseDto<>(orderDto,message,status);
    }
}
