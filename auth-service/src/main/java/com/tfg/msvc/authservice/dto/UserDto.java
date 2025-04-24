package com.tfg.msvc.authservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class UserDto {
    private String idUsuario;
    private String nombre;
    private String apellido;
    private String cp;
    private String ciudad;
    private String provincia;
    private String email;
    private String usuario;
    private String contraseña;
    private String genero;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;
    private Set<RoleDto> roles;

}