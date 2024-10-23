package com.library.service;

import com.library.dto.ProductDto;
import com.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {


    //Admin
    List<ProductDto> findAll();
    ProductDto findById(Long id);
    Product findByName(String name);
    Product save(MultipartFile imageProduct, ProductDto productDto);
    void deleteById(Long id);
    Product update(MultipartFile imageProduct, ProductDto productDto);
    Page<ProductDto> pageProducts(int pageNo);
    Page<ProductDto> searchProducts(int pageNo, String keyword);

    //Customer
List<Product> getAllProducts();

List<Product> listViewProducts();

Product getProductById(Long id);

List<Product> getProductByCategoryId(Long categoryId);

    List<Product> getProductsInCategory(Long categoryId);

    List<Product> filterHighToLowPrice();

    List<Product> filterLowToHighPrice();
}