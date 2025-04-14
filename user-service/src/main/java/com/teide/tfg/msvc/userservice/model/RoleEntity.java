package com.teide.tfg.msvc.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Table(name = "rol")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;
    private String tipo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_permisos",
            joinColumns = @JoinColumn(name = "id_rol",referencedColumnName = "id_rol"),
            inverseJoinColumns = @JoinColumn(name = "id_permisos",referencedColumnName = "id_permisos")
    )
    private Set<PermissionEntity> permissions;

}
