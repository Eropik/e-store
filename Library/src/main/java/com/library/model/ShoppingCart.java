package com.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Data
@Entity
@AllArgsConstructor
@Table(name="shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shopping_cart_id")
    private Long id;

    private int totalItems;

    private double totalPrice;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;




    @OneToMany(cascade = CascadeType.DETACH,fetch = FetchType.LAZY, mappedBy = "cart")
    private Set<CartItem> cartItems;

    public ShoppingCart() {
        this.cartItems = new HashSet<>();
        this.totalItems = 0;
        this.totalPrice = 0.0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ShoppingCart that = (ShoppingCart) obj;
        return Objects.equals(id, that.id);
    }


}
