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

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) throws EntityNotFoundException {
        try {
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Error found category with id " + id);
        }
    }


    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getCategoryAndProduct() {
        return categoryRepository.getCategoryAndProduct();
    }




}