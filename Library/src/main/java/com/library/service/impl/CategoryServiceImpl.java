package com.library.service.impl;


import com.library.dto.CategoryDto;
import com.library.dto.ProductDto;
import com.library.model.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        Category categorySave = new Category();
        categorySave.setName(category.getName());
        return categoryRepository.save(categorySave);

    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = categoryRepository.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return categoryRepository.save(categoryUpdate);
    }


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getById(id);
        categoryRepository.save(category);
    }



    @Override
    public List<CategoryDto> getCategoriesAndSize() {
        return categoryRepository.getCategoriesAndSize();
    }


}