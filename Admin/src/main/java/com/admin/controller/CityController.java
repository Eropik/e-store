package com.admin.controller;

import com.library.model.Category;
import com.library.model.City;
import com.library.model.Country;
import com.library.service.CategoryService;
import com.library.service.CityService;
import com.library.service.CountryService;
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
public class CityController {

    private final CityService cityService;
private final CountryService countryService;


    @PostMapping("/m-save-city")
    public String saveCity(@ModelAttribute("cityNew") City city, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Проверяем, что страна была выбрана
            if (city.getCountry() == null ) {
                redirectAttributes.addFlashAttribute("error", "Не выбрана страна для города.");
                return "redirect:/m-cities";  // Вернуть пользователя на страницу, если страна не выбрана
            }

            // Получаем объект страны по ID
            Long countryId = city.getCountry().getId();
            Country country = countryService.findById(countryId).orElse(null);

            if (country == null) {
                redirectAttributes.addFlashAttribute("error", "Не найдена страна с таким ID.");
                return "redirect:/m-cities";
            }

            // Устанавливаем объект страны в город
            city.setCountry(country);
            cityService.save(city);  // Сохраняем город с привязанной страной
            redirectAttributes.addFlashAttribute("success", "Город успешно добавлен!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Ошибка, возможно, уже есть город с таким именем.");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Ошибка сервера.");
        }
        return "redirect:/m-cities";  // Перенаправляем на страницу городов
    }






    @GetMapping("/m-cities")
    public String cities(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Manage City");
        List<City> cities = cityService.findAll();
        model.addAttribute("cities", cities);
        model.addAttribute("size", cities.size());
        model.addAttribute("cityNew", new City());
        return "cities";


    }


    @RequestMapping(value = "/m-find-city", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<City> findCity(Long id) {
        return cityService.findById(id);
    }

    @GetMapping("/m-update-city")
    public String update(City city, RedirectAttributes redirectAttributes) {
        try {
            cityService.update(city);
            redirectAttributes.addFlashAttribute("success", "Update successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error from server, please check again!");
        }
        return "redirect:/m-cities";
    }

    @RequestMapping(value = "/m-delete-city", method = {RequestMethod.GET, RequestMethod.PUT})
    public String delete(Long id, RedirectAttributes redirectAttributes) {
        try {
            cityService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Deleted successfully!");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error server");
        }
        return "redirect:/m-cities";
    }



}
