package com.admin.controller;


import com.library.dto.ProductDto;
import com.library.model.Category;
import com.library.model.Product;
import com.library.service.CategoryService;
import com.library.service.ProductService;
import com.library.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    private final CategoryService categoryService;


    @GetMapping("/products")
    public String products(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<ProductDto> products = productService.allProduct();

        for (ProductDto product : products) {
            product.setImageBase64(
                    ImageUtil.encodeToBase64(
                            ImageUtil.decompressImage(product.getImage())));
        }

        model.addAttribute("products", products);
        model.addAttribute("size", products.size());
        return "products";
    }

    @GetMapping("/products/{pageNo}")
    public String allProducts(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.getAllProducts(pageNo);
        model.addAttribute("title", "Manage Products");

        model.addAttribute("size", products.getSize());
        for (ProductDto product : products) {
            product.setImageBase64(
                    ImageUtil.encodeToBase64(
                            ImageUtil.decompressImage(product.getImage())));
        }
        model.addAttribute("products", products);

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "products";
    }

    @GetMapping("1")
    public String searchProduct1(@PathVariable("pageNo") int pageNo,
                                @RequestParam(value = "keyword") String keyword,
                                Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);

       /* for (ProductDto product : products) {
            product.setImageBase64(
                    ImageUtil.encodeToBase64(
                            ImageUtil.decompressImage(product.getImage())));
        }*/
        for (ProductDto product : products) {
            product.setImageBase64(
                   null);}


        model.addAttribute("products", products);

        model.addAttribute("title", "Result Search Products");
        model.addAttribute("size", products.getSize());

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "1";

    }

    @GetMapping("/search-products/{pageNo}")
    public String searchProduct(@PathVariable("pageNo") int pageNo,
                                @RequestParam(value = "keyword") String keyword,
                                Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Page<ProductDto> productPage = productService.searchProducts(pageNo, keyword);

        List<ProductDto> products = productPage.getContent();


          for (ProductDto product : products) {
            product.setImageBase64(
                    ImageUtil.encodeToBase64(
                            ImageUtil.decompressImage(product.getImage())));
        }

        model.addAttribute("products", products);


        model.addAttribute("size", products.size());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", productPage.getTotalPages());

        model.addAttribute("title", "Результаты поиска продуктов");

        return "product-result";
    }


    @GetMapping("/add-product")
    public String addProductPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Add Product");
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("productDto") ProductDto product,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }
            productService.save(imageProduct.getBytes(), product);
            redirectAttributes.addFlashAttribute("success", "Add new product successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to add new product!");
        }
        return "redirect:/products/0";
    }

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAll();
        ProductDto productDto = productService.getById(id);


        byte[] decompressedImage = ImageUtil.decompressImage(productDto.getImage());
        productDto.setImageBase64(ImageUtil.encodeToBase64(decompressedImage));


        model.addAttribute("title", "Add Product");
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);

        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes, Principal principal) {
        try {
            if (principal == null) {
                return "redirect:/login";
            }

            productService.update(imageProduct.getBytes(), productDto);

            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server, please try again!");
        }
        return "redirect:/products/0";
    }





    //for Postman

    @PostMapping("/add-product_1")
    public ResponseEntity<?> addProductPage(
            @RequestParam("imageProduct") MultipartFile imageProduct,
            @ModelAttribute ProductDto productDto,
            Principal principal) {
        Product savedProduct = null;
        try {
            savedProduct = productService.save(imageProduct.getBytes(), productDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (savedProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product not saved");
        }
    }


}
