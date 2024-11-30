package com.library.repository;

import com.library.model.CartItem;
import com.library.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
