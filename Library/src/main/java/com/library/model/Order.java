package com.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    private Date orderDate;

    private Date deliveryDate;

    private double totalPrice;

    private double tax;

    private String orderStatus;

    private int quantity;

    private String paymentMethod;

    private boolean isAccept;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy ="order" )
    private List<OrderDetail> orderDetailList;
}
