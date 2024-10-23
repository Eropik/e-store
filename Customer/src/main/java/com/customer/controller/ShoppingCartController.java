package com.customer.controller;

import com.library.model.Customer;
import com.library.model.Product;
import com.library.model.ShoppingCart;
import com.library.service.CustomerService;
import com.library.service.ProductService;
import com.library.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ShoppingCartController {

    private final CustomerService customerService;
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;


    @GetMapping("/cart")
    public String cart(Model model, Principal principal, HttpSession httpSession) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        if (shoppingCart == null) {
            model.addAttribute("check", "No items in cart");
             }
httpSession.setAttribute("totalItems", shoppingCart.getTotalItems());
        model.addAttribute("subTotal",  shoppingCart.getTotalPrice());

        model.addAttribute("shoppingCart", shoppingCart);
        return "cart";
    }


    @PostMapping("/add-to-cart")
    public String addItemToCart(HttpServletRequest request,
                                Model model,
                                Principal principal,
                                @RequestParam("id") Long productId,
                                @RequestParam(value = "quantity",
                                        required = false,
                                        defaultValue = "1") Integer quantity) {
        if (principal == null) {
            return "redirect:/login";
        }
        Product product = productService.getProductById(productId);
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(product, quantity, customer);
        return "redirect:" + request.getHeader("Referer");
    }


    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("quantity") int quantity,
                             @RequestParam("id") Long productId,
                             Model model,
                             Principal principal
    ) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            Product product = productService.getProductById(productId);
            ShoppingCart cart = shoppingCartService.updateItemInCart(product, quantity, customer);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")

    public String removeItemFromCart(@RequestParam("id") Long productId,
                                     Model model,
                                     Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            Product product = productService.getProductById(productId);
            ShoppingCart cart = shoppingCartService.removeItemFromCart(product, customer);
            model.addAttribute("shoppingCart", cart);
            return "redirect:/cart";
        }
    }
}
