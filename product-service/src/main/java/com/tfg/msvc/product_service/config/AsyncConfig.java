package com.tfg.msvc.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean("executor")
    ExecutorService taskExecutor() {

        int maxThreads = Runtime.getRuntime().availableProcessors() * 4;
        return Executors.newFixedThreadPool(maxThreads);


    }
}
