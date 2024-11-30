package com.library.service;

import com.library.dto.ProductDto;
import com.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<ProductDto> products();

    List<ProductDto> allProduct();

    Product save(byte[] imageProduct, ProductDto product);

    Product update(byte[] imageProduct, ProductDto productDto);

    ProductDto getById(Long id);

    List<ProductDto> randomProduct();

    Page<ProductDto> searchProducts(int pageNo, String keyword);

    Page<ProductDto> getAllProducts(int pageNo);


    List<ProductDto> findAllByCategory(String category);


    List<ProductDto> filterHighProducts();

    List<ProductDto> filterLowerProducts();

    List<ProductDto> listViewProducts();

    List<ProductDto> findByCategoryId(Long id);

    List<ProductDto> searchProducts(String keyword);
}