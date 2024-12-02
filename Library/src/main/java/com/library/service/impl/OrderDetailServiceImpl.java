package com.library.service.impl;

import com.library.dto.ProductDto;
import com.library.model.Order;
import com.library.model.Product;
import com.library.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    @Override
   @Transactional
    public List<Product> getProducts(Order order) {
        List<Product> products=order.getOrderDetailList().stream().map(el->el.getProduct()).toList();
        return products;
    }
}
