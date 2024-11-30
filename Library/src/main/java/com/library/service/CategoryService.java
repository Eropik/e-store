package com.library.service;

import com.library.dto.CategoryDto;
import com.library.dto.ProductDto;
import com.library.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    Category save(Category category);

    Category update(Category category);



    List<Category> findAll();

    Optional<Category> findById(Long id);

    void deleteById(Long id);



    List<CategoryDto> getCategoriesAndSize();
}