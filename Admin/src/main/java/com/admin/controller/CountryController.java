package com.admin.controller;

import com.library.model.Country;  // Changed from City to Country
import com.library.service.CountryService;  // Changed from CityService to CountryService
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/m-countries")
    public String countries(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage Country");
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("size", countries.size());
        model.addAttribute("countryNew", new Country());
        return "countries";
    }

    @PostMapping("/m-save-country")
    public String saveCountry(@ModelAttribute("countryNew") Country country, Model model, RedirectAttributes redirectAttributes) {
        try {
            countryService.save(country);
            model.addAttribute("countryNew", country);
            redirectAttributes.addFlashAttribute("success", "Add successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of country, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            model.addAttribute("countryNew", country);
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/m-countries";
    }

    @RequestMapping(value = "/m-find-country", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<Country> findCountry(Long id) {
        return countryService.findById(id);
    }

    @GetMapping("/m-update-country")
    public String update(Country country, RedirectAttributes redirectAttributes) {
        try {
            countryService.update(country);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of country, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server, please check again!");
        }
        return "redirect:/m-countries";
    }

    @RequestMapping(value = "/m-delete-country", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            countryService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of country, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/m-countries";
    }
}
