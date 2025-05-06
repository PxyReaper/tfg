package com.teide.tfg.order_service.controller;

import com.teide.tfg.order_service.dto.OrderDto;
import com.teide.tfg.order_service.dto.ResponseDto;
import com.teide.tfg.order_service.factory.ResponseFactory;
import com.teide.tfg.order_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseDto<OrderDto>> findById(@PathVariable("id") Long id){
        OrderDto orderDto = orderService.findById(id);
        ResponseDto<OrderDto> response =  ResponseFactory.generateSuccesResponse(orderDto,"Pedido encontrado exitosamente",
                HttpStatus.OK.value(),null,null,null);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<ResponseDto<List<OrderDto>>> findALl(@RequestParam(name = "page",defaultValue ="0") int page, @RequestParam(name = "size"
    ,defaultValue = "10") int size){
        Page<OrderDto> orderDtos = orderService.findALl(page,size);
        List<OrderDto> orderDtoList = orderDtos.stream().toList();
        int totalPages = orderDtos.getTotalPages();
        ResponseDto<List<OrderDto>> response = ResponseFactory.generateSuccesResponse(orderDtoList,null,200,page,size,totalPages);
        return  ResponseEntity.ok(response);
    }


}
