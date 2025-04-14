package com.teide.tfg.msvc.userservice.model;

import com.teide.tfg.msvc.userservice.enums.UserGenero;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Table(name = "usuarios")
@Entity
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_usuario")
    private String idUsuario;
    private String nombre;
    private String apellido;
    private String cp;
    private String ciudad;
    private String provincia;
    private String email;
    private String usuario;
    private String contraseña;
    private UserGenero genero;
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

}
