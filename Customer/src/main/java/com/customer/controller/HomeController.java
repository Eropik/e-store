package com.customer.controller;

import com.library.dto.ProductDto;
import com.library.model.Category;
import com.library.model.Customer;
import com.library.model.Product;
import com.library.model.ShoppingCart;
import com.library.service.CategoryService;
import com.library.service.CustomerService;
import com.library.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final CustomerService customerService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session) {
        if (principal != null) {
            session.setAttribute("username", principal.getName());
            Customer customer  = customerService.findByUsername(principal.getName());
            ShoppingCart cart = customer.getShoppingCart();
            session.setAttribute("totalItems", cart.getTotalItems());
        } else {
            session.removeAttribute("username");
        }

        return "home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        List<ProductDto> productDtos = productService.findAll();
        model.addAttribute("products", productDtos);

        return "index";
    }


}
