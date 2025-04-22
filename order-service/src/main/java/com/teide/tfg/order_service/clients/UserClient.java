package com.teide.tfg.order_service.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service")
public class UserClient {

}
