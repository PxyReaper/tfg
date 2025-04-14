package com.teide.tfg.msvc.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "permisos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permisos")
    private Long idPermisos;
    private String tipo;

}
