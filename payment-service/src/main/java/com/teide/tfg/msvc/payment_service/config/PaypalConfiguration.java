package com.teide.tfg.msvc.payment_service.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfiguration {
    @Value("${paypal.clientId}")
    private String clientId;
    @Value("${paypal.clientSecret}")
    private String clientSecret;
    @Bean
    public PayPalHttpClient getPayPalHttpClient() {
        return new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId,clientSecret));
    }
    @PostConstruct
    public void init() {
        System.out.println(clientId + " cliente paypal");
        System.out.println(clientSecret + " cliente secreto paypal");
    }
}
