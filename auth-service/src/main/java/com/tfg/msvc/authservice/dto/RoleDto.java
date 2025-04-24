package com.tfg.msvc.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoleDto {
    private Long idRol;
    private String tipo;
    private Set<PermissionDto> permissions;
}
