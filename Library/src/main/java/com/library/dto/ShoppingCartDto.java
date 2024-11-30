package com.library.dto;

import com.library.model.CartItem;
import com.library.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {

    private Long id;

    private int totalItems;

    private double totalPrice;

    private Customer customer;

    private Set<CartItemDto> cartItems;

}
