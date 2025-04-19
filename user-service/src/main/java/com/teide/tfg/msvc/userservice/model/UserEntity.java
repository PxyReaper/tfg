package com.teide.tfg.msvc.userservice.model;

import com.teide.tfg.msvc.userservice.enums.UserGenero;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Table(name = "usuarios")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(
           name = "usuarios_rol",
           joinColumns = @JoinColumn(name = "id_usuario",referencedColumnName = "id_usuario"),
           inverseJoinColumns = @JoinColumn(name = "id_rol",referencedColumnName = "id_rol")
   )
    private Set<RoleEntity> roles;

}
