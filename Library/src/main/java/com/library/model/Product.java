package com.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Arrays;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products", uniqueConstraints = @UniqueConstraint(columnNames = {"name","image"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;
    private int currentQuantity;

    private String name;
    private String description;

    @Override
    public String toString() {
        return "Product{" +
                "category=" + category +
                ", id=" + id +
                ", currentQuantity=" + currentQuantity +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image= lot of ... " +
                ", salePrice=" + salePrice +
                ", costPrice=" + costPrice +
                '}';
    }

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] image;

    private double salePrice;
    private double costPrice;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="category_id", referencedColumnName ="category_id" )
    private Category category;


}
