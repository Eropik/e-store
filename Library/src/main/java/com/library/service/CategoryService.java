package com.library.service;

import com.library.dto.CategoryDto;
import com.library.dto.ProductDto;
import com.library.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    //Admin
    List<Category> findAll();

    List<Category> findByName(String name);

    Category save(Category category);

    Category getById(Long id);

    void deleteById(Long id);

    //Customer
    List<CategoryDto> getCategoryAndProduct();

}