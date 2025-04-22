package com.teide.tfg.order_service.dto;

import java.util.Set;

public class PermissionDto {
    private Long idRol;
    private String tipo;
    private Set<PermissionDto> permissions;
}
