package com.library.service;

import com.library.model.Order;
import com.library.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService  {
    List<Product> getProducts(Order order);
}
