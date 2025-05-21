package com.teide.tfg.msvc.payment_service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teide.tfg.msvc.payment_service.dto.PaymentProducerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void sendPayment(PaymentProducerDto payment) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            String object = mapper.writeValueAsString(payment);
            kafkaTemplate.send("payment-topic", object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

    }
}
