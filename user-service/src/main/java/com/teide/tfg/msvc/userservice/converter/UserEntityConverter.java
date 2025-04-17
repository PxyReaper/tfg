package com.teide.tfg.msvc.userservice.converter;

import com.teide.tfg.msvc.userservice.dto.PermissionDto;
import com.teide.tfg.msvc.userservice.dto.RoleDto;
import com.teide.tfg.msvc.userservice.dto.UserDto;
import com.teide.tfg.msvc.userservice.model.UserEntity;

import javax.management.relation.Role;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEntityConverter {
    public static UserDto convertUserEntityToUserDto(UserEntity userEntity) {
        Set<RoleDto> roleDto = userEntity.getRoles().stream().map(r -> {
            Set<PermissionDto> permissionDto = r.getPermissions().stream().map(p -> new PermissionDto(
                    p.getIdPermisos(), p.getTipo()
            )).collect(Collectors.toSet());
            return new RoleDto(r.getIdRol(),r.getTipo(),permissionDto);
        }).collect(Collectors.toSet());
        return new UserDto(userEntity.getIdUsuario(),userEntity.getNombre(),userEntity.getApellido(),
                userEntity.getCp(),userEntity.getCiudad(),userEntity.getProvincia(),userEntity.getEmail(),
                userEntity.getUsuario(),userEntity.getGenero(),userEntity.getFechaNacimiento(),roleDto
                );

    }
}
