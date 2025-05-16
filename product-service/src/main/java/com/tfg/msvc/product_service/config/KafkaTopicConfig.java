package com.tfg.msvc.product_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic generateTopic() {
        Map<String, String> map = new HashMap<>();
        map.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        map.put(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(Duration.ofMinutes(20).toMillis()));
        map.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824");//TAMAÑO MAXIMO DEL SEGMENTO
        map.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000012"); //TAMAÑO EN BYTES POR CADA MENSAJE
        return TopicBuilder.name("orders-topic")
                .partitions(2)
                .replicas(1)
                .configs(map)
                .build();
    }
}
