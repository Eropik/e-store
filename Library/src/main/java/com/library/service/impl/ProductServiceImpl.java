package com.library.service.impl;


import com.library.dto.ProductDto;
import com.library.model.Product;
import com.library.repository.ProductRepository;
import com.library.service.ProductService;
import com.library.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ImageUpload imageUpload;

    //Admin
    @Override
    public List<ProductDto> findAll() {
        List<Product> productList = productRepository.findAll();
        return transferListData(productList);
    }

    @Override
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            System.out.println("Product with id " + id + " not found.");
            return null;
        }

        return setProductDtoFields(product);
    }

    @Override
    public Product findByName(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Product name is null or empty.");
            return null;
        }

        Product product = productRepository.findByName(name);

        if (product == null) {
            System.out.println("Product with name " + name + " not found.");
        }

        return product;
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product product = productRepository.findById(productDto.getId()).orElse(null);
            if (product != null) {
                setProductFields(product, productDto, imageProduct);
                return productRepository.save(product);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Page<ProductDto> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> products =transferListData(productRepository.findAll());
        try {
            return toPage(products,pageable);
        } catch (ClassCastException e) {
           e.printStackTrace();
            System.out.println("Cast exception");
           return null;
        }
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        /*Pageable pageable = PageRequest.of(pageNo, 5);
        List<ProductDto> productDtoList  =transferListData(productRepository.searchProductList(keyword));
        return (Page<ProductDto>) toPage(productDtoList,pageable);
           */


        Pageable pageable = PageRequest.of(pageNo, 5);
        return toPage( transferListData(productRepository.searchProductList(keyword)), pageable);
    }



    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = null;
        try {
            product = new Product();
            setProductFields(product, productDto, imageProduct);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        }
    }

    //Customer

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProduct();
    }

    @Override
    public List<Product> listViewProducts(){
        return productRepository.listViewProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getProductByCategoryId(Long categoryId) {
        return productRepository.findProductByCategoryId(categoryId);
    }

    private Page toPage(List list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min((startIndex + pageable.getPageSize()), list.size());

        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<ProductDto> products() {
        return transferListData(productRepository.getAllProduct());
    }

    public List<ProductDto> allProduct() {
        return transferListData(productRepository.findAll());
    }

    @Override
    public List<Product> getProductsInCategory(Long categoryId) {
        return productRepository.getProductsInCategory(categoryId);
    }

    @Override
    public List<Product> filterHighToLowPrice() {
        return productRepository.filterHighToLowPrice();
    }

    @Override
    public List<Product> filterLowToHighPrice() {
        return productRepository.filterLowToHighPrice();
    }


    private void setProductFields(Product product, ProductDto productDto, MultipartFile imageProduct) throws Exception {
        if (imageProduct != null && !imageProduct.isEmpty()) {
            if (imageUpload.checkExisted(imageProduct)) {
                System.out.println("Image already exists: " + imageProduct.getOriginalFilename());
            } else {
                if (imageUpload.uploadImage(imageProduct)) {
                    System.out.println("Uploaded image successfully");
                    product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
        } else {
            product.setImage(productDto.getImage());
        }
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCategory(productDto.getCategory());
        System.out.println(product.getName() + " : " + product.getCostPrice());
    }

    private ProductDto setProductDtoFields(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setCostPrice(product.getCostPrice());
        productDto.setSalePrice(product.getSalePrice());
        productDto.setImage(product.getImage());
        productDto.setCurrentQuantity(product.getCurrentQuantity());
        productDto.setCategory(product.getCategory());
        return productDto;
    }

    private List<ProductDto> transferListData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setDescription(product.getDescription());
            productDto.setImage(product.getImage());
            productDto.setCategory(product.getCategory());
            productDtos.add(productDto);
        }
        return productDtos;
    }
}