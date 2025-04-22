package com.teide.tfg.order_service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long idRol;
    private String tipo;
    private Set<PermissionDto> permissions;
}
