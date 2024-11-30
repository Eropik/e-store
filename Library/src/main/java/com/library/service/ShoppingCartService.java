package com.library.service;

import com.library.dto.ProductDto;
import com.library.dto.ShoppingCartDto;
import com.library.model.CartItem;
import com.library.model.Customer;
import com.library.model.Product;
import com.library.model.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

public interface ShoppingCartService {

    //List<Product> getProducts(List<CartItem> cartItems);

    List<CartItem> getCartItems (ShoppingCart cart);

    ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username);

    ShoppingCart updateCart(ProductDto productDto, int quantity, String username);

    ShoppingCart removeItemFromCart(ProductDto productDto, String username);

    ShoppingCartDto addItemToCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCartDto updateCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCartDto removeItemFromCartSession(ShoppingCartDto cartDto, ProductDto productDto, int quantity);

    ShoppingCart combineCart(ShoppingCartDto cartDto, ShoppingCart cart);


    void deleteCartById(Long id);

    ShoppingCart getCart(String username);
}
