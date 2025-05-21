package com.teide.tfg.msvc.payment_service.controller;

import com.teide.tfg.msvc.payment_service.dto.Cart;
import com.teide.tfg.msvc.payment_service.dto.Example;
import com.teide.tfg.msvc.payment_service.model.CompletedOrder;
import com.teide.tfg.msvc.payment_service.model.PaymentOrder;
import com.teide.tfg.msvc.payment_service.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/init")
    public ResponseEntity<PaymentOrder> init(@RequestBody Cart carrito, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replaceFirst("Bearer ", "");

        System.out.println("entre");
       PaymentOrder order = paymentService.createOrder(carrito,token);
       return ResponseEntity.ok(order);
    }


    @GetMapping("/capture")
    public ResponseEntity<CompletedOrder> completePayment(@RequestParam("token") String token){
        CompletedOrder completedOrder = paymentService.completePayment(token);
        return ResponseEntity.ok(completedOrder);
    }
    @GetMapping("/cancel")
    public String cancelPayment(){
        return "se ha cancelado el pago";
    }
}
