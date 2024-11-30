package com.library.dto;

import com.library.model.Category;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
    private byte[] image;

    private String ImageBase64;

    private Category category;
    private String currentPage;
}

