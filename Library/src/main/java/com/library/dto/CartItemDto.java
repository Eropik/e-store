package com.library.dto;

import com.library.model.Product;
import com.library.model.ShoppingCart;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemDto {
    private Long id;
    private int quantity;
    private double unitPrice;
    private ShoppingCartDto shoppingCart;
    private ProductDto product;


    //private ShoppingCartDto cart;


}
