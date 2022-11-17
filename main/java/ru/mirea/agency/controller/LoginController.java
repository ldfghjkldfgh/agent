package ru.mirea.agency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
@Controller
public class LoginController {

    @GetMapping
    public String loginForm() {
        return "login";
    }

    @ModelAttribute(value = "activePage")
    public String activePage() {
        return "login";
    }
}
