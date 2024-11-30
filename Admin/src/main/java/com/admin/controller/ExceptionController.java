package com.admin.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("exceptionMessage", ex.getClass().getName());
        model.addAttribute("stackTrace", Arrays.toString(ex.getStackTrace()));
        return "error";
    }
}
