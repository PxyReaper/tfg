package com.teide.tfg.msvc.userservice.repository;

import com.teide.tfg.msvc.userservice.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    public RoleEntity findRoleEntitiesByTipoIgnoreCase(String tipo);
}
