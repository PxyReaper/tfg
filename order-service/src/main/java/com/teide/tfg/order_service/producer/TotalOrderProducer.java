package com.teide.tfg.order_service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teide.tfg.order_service.dto.TotalOrderProducerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TotalOrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(TotalOrderProducerDto totalOrderProducerDto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(totalOrderProducerDto);
            kafkaTemplate.send("total-order-topic",json).thenAccept( result ->
                    System.out.println(" Se ha enviado el evento correctamente"))
                    .exceptionally(err ->{ System.out.println("Ha ocurrido un error " + err.getMessage());
                    return null;
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
