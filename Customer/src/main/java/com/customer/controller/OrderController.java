package com.customer.controller;

import com.library.model.Customer;
import com.library.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CustomerService customerService;

    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login ";
        }
        Customer customer = customerService.findByUsername(principal.getName());

        if (customer.getPhoneNumber().trim().isEmpty() ||
                customer.getAddress().trim().isEmpty() ||
                customer.getCity().trim().isEmpty() ||
                customer.getCountry().trim().isEmpty()) {
            model.addAttribute("information", "You need update your information before check out");
            model.addAttribute("customer", customer);
            return "account";

        }
        //toDo

        return "check-out";
    }
}
