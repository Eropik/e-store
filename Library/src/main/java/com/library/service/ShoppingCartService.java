package com.library.service;

import com.library.model.Customer;
import com.library.model.Product;
import com.library.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);

    ShoppingCart removeItemFromCart(Product product, Customer customer);

    ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);
}
