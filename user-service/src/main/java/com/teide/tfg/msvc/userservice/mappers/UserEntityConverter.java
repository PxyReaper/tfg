package com.teide.tfg.msvc.userservice.mappers;

import com.teide.tfg.msvc.userservice.dto.PermissionDto;
import com.teide.tfg.msvc.userservice.dto.RoleDto;
import com.teide.tfg.msvc.userservice.dto.UserDto;
import com.teide.tfg.msvc.userservice.model.PermissionEntity;
import com.teide.tfg.msvc.userservice.model.RoleEntity;
import com.teide.tfg.msvc.userservice.model.UserEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEntityConverter {
    public static UserDto convertUserEntityToUserDto(UserEntity userEntity) {
        Set<RoleDto> roleDto = userEntity.getRoles()  != null ? userEntity.getRoles().stream().map(r -> {
            Set<PermissionDto> permissionDto = r.getPermissions() != null ? r.getPermissions().stream().map(p -> new PermissionDto(
                    p.getIdPermisos(), p.getTipo()
            )).collect(Collectors.toSet()) : new HashSet<>();
            return new RoleDto(r.getIdRol(),r.getTipo(),permissionDto);
        }).collect(Collectors.toSet()): new HashSet<>();
        return new UserDto(userEntity.getIdUsuario(),userEntity.getNombre(),userEntity.getApellido(),
                userEntity.getCp(),userEntity.getCiudad(),userEntity.getProvincia(),userEntity.getEmail(),
                userEntity.getUsuario(),userEntity.getContraseña(),userEntity.getGenero(),userEntity.getFechaNacimiento(),roleDto
                );

    }

    public static UserEntity convertUserDtoToUserEntity(UserDto userDto) {
        Set<RoleEntity> roleEntities = userDto.getRoles() != null ? userDto.getRoles().stream().map(r -> {
            Set<PermissionEntity> permissionEntities = r.getPermissions() != null ? r.getPermissions().stream().
                    map(p -> new PermissionEntity(p.getIdPermisos(),p.getTipo())).collect(Collectors.toSet())
                    : new HashSet<>();
            return new RoleEntity(r.getIdRol(),r.getTipo(),permissionEntities);
        }).collect(Collectors.toSet()) : new HashSet<>();
        return new UserEntity(userDto.getIdUsuario(),userDto.getNombre(),userDto.getApellido(),userDto.getCp()
                ,userDto.getCiudad(),userDto.getProvincia(),userDto.getEmail(),userDto.getUsuario(),
                userDto.getContraseña(),userDto.getGenero(),userDto.getFechaNacimiento(),roleEntities);
    }
}
