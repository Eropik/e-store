package com.library.service;

import com.library.model.CartItem;
import com.library.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public interface CartItemService {

     List<Product> getProducts(List<CartItem> cartItems);
}
