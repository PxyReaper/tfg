package com.teide.tfg.msvc.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "usuarios")
@Entity
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_usuario")
    private String idUsuario;

}
