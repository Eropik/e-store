package com.customer.controller;

import com.library.model.City;
import com.library.model.Customer;
import com.library.service.CityService;
import com.library.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final CustomerService customerService;
    private final CityService cityService;

    @GetMapping("/account")
    public String accountHome(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Customer customer = customerService.findByUsername(principal.getName());
        model.addAttribute("customer", customer);


        return "account";
    }

    @RequestMapping(value="/update-infor", method={RequestMethod.GET,RequestMethod.PUT})
    public String updateCustomer(@ModelAttribute("customer") Customer customer,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }


        Customer customerSaved = customerService.saveInfor(customer);

        redirectAttributes.addFlashAttribute("customer", customerSaved);
        return "redirect:/account";
    }


}
