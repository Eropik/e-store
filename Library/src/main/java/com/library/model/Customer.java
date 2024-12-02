package com.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "image", "phone_number"}))
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name", referencedColumnName = "id")
    private City city;

     private String country;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "customer_role", joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Collection<Role> roles;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private ShoppingCart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Customer() {
        this.cart = new ShoppingCart();
        this.orders = new ArrayList<>();
    }

}
