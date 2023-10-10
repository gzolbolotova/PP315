package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @GetMapping
    public String basePage(Model model, @AuthenticationPrincipal User thisUser) {
        model.addAttribute("thisUser", thisUser);
        return "index";
    }
}
