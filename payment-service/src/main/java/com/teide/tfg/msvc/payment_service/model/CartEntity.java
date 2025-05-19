package com.teide.tfg.msvc.payment_service.model;
import com.teide.tfg.msvc.payment_service.dto.ProductQuantity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "carrito")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartEntity {
    @Id
    @Column(name = "id_carrito")
    private String idCart;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "carrito_productos",
            joinColumns = @JoinColumn(name = "id_carrito", referencedColumnName = "id_carrito"),
            inverseJoinColumns = @JoinColumn(name = "id_producto_cantidad", referencedColumnName = "id_producto_cantidad")
    )


    private Set<ProductQuantityEntity> product;
    private BigDecimal price;
}
