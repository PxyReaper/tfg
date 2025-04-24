package com.tfg.msvc.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class RoleDto {
    private Long idRol;
    private String tipo;
    private Set<PermissionDto> permissions;
}
