package com.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product", uniqueConstraints = @UniqueConstraint(columnNames = {"name","images"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;
    private int currentQuantity;

    private String name;
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String image;

    private double salePrice;
    private double costPrice;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="category_id", referencedColumnName ="category_id" )
    private Category category;


}
