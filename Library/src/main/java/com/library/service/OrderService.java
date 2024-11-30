package com.library.service;

import com.library.model.Order;
import com.library.model.ShoppingCart;

import java.util.List;

public interface OrderService {

     Order save(ShoppingCart shoppingCart);

    List<Order> findAll(String username);

    List<Order> findAllOrders();

    Order acceptOrder(Long id);

    void cancelOrder(Long id);

}
