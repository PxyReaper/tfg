package com.teide.tfg.msvc.userservice.repository;

import com.teide.tfg.msvc.userservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    
}
