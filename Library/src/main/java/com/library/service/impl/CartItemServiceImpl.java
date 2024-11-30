package com.library.service.impl;

import com.library.model.CartItem;
import com.library.model.Product;
import com.library.service.CartItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Override
    @Transactional
    public List<Product> getProducts(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toList());
    }
}
