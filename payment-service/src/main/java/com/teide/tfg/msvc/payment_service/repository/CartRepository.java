package com.teide.tfg.msvc.payment_service.repository;

import com.teide.tfg.msvc.payment_service.model.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, String> {
}
