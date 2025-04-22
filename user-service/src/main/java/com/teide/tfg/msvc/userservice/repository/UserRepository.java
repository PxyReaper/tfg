package com.teide.tfg.msvc.userservice.repository;

import com.teide.tfg.msvc.userservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    public Optional<UserEntity> findUserEntityByEmail(String email);
    
}
