package com.customer.controller;

import com.library.dto.CategoryDto;
import com.library.model.Category;
import com.library.model.Product;
import com.library.service.CategoryService;
import com.library.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model) {
        List<Product> products = productService.getAllProducts();
        List<Product> listViewProduct = productService.listViewProducts();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        model.addAttribute("viewProduct", listViewProduct);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryDtoList);
        return "shop";
    }


    @GetMapping("/find-product/{id}")
    public String findProduct(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        Long categoryId = product.getCategory().getId();
        List<Product> products = productService.getProductByCategoryId(categoryId);
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products-in-category/{id}")
    public String getProductsInCategory(@PathVariable("id") Long categoryId, Model model) {
        Category category = categoryService.getById(categoryId);
        List<Product> products = productService.getProductsInCategory(categoryId);
        List<CategoryDto> categories = categoryService.getCategoryAndProduct();
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "products-in-category";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
        List<Category> categories = categoryService.findAll();
        List<Product> products = productService.filterHighToLowPrice();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "filter-high-price";
    }

    @GetMapping("/low-price")
    public String filterLowPrice(Model model) {
        List<Category> categories = categoryService.findAll();
        List<CategoryDto> categoryDtoList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.filterLowToHighPrice();
        model.addAttribute("categoryDtoList", categoryDtoList);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "filter-low-price";
    }


}
