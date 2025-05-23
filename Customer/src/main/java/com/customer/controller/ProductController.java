package com.customer.controller;

import com.library.dto.CategoryDto;
import com.library.dto.ProductDto;
import com.library.model.Category;
import com.library.service.CategoryService;
import com.library.service.ProductService;
import com.library.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("page", "Products");
        model.addAttribute("title", "Menu");
        List<Category> categories = categoryService.findAll();



        List<ProductDto> products = setImages(productService.products());


        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index";
    }

    @GetMapping("/product-detail/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        ProductDto product = productService.getById(id);
        List<ProductDto> productDtoList = setImages(  productService.findAllByCategory(product.getCategory().getName()));

        product.setImageBase64(ImageUtil.encodeToBase64(
                ImageUtil.decompressImage(product.getImage())));


        model.addAttribute("products", productDtoList);
        model.addAttribute("title", "Product Detail");
        model.addAttribute("page", "Product Detail");
        model.addAttribute("productDetail", product);
        return "product-detail";
    }



    @GetMapping("/shop-detail")
    public String shopDetail(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);

        List<ProductDto> products = setImages(productService.randomProduct());

        List<ProductDto> listView = setImages(productService.listViewProducts());


        model.addAttribute("productViews", listView);
        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("products", products);
        return "shop-detail";
    }

    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);

        List<ProductDto> products = setImages(productService.filterHighProducts());

        List<ProductDto> listView = setImages(productService.listViewProducts());

        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("productViews", listView);
        model.addAttribute("products", products);
        return "shop-detail";
    }


    @GetMapping("/lower-price")
    public String filterLowerPrice(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesAndSize();
        model.addAttribute("categories", categories);


        List<ProductDto> products = setImages(productService.filterLowerProducts());

        List<ProductDto> listView = setImages(productService.listViewProducts());




        model.addAttribute("productViews", listView);

        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("products", products);
        return "shop-detail";
    }

    @GetMapping("/find-products/{id}")
    public String productsInCategory(@PathVariable("id") Long id, Model model) {
        List<CategoryDto> categoryDtos = categoryService.getCategoriesAndSize();



        List<ProductDto> productDtos = setImages(productService.findByCategoryId(id));

        List<ProductDto> listView = setImages(productService.listViewProducts());

        model.addAttribute("productViews", listView);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("title", productDtos.get(0).getCategory().getName());
        model.addAttribute("page", "Product-Category");
        model.addAttribute("products", productDtos);
        return "products";
    }


    @GetMapping("/search-product")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        List<CategoryDto> categoryDtos = categoryService.getCategoriesAndSize();

        List<ProductDto> productDtos = setImages(productService.searchProducts(keyword));

        List<ProductDto> listView = setImages(productService.listViewProducts());

        model.addAttribute("productViews", listView);
        model.addAttribute("categories", categoryDtos);
        model.addAttribute("title", "Search Products");
        model.addAttribute("page", "Result Search");
        model.addAttribute("products", productDtos);
        return "products";
    }


    private List<ProductDto> setImages(List<ProductDto> products) {
        for (ProductDto product : products) {
            product.setImageBase64(
                    ImageUtil.encodeToBase64(
                            ImageUtil.decompressImage(product.getImage())
                    )
            );
        }
        return products;
    }
}
