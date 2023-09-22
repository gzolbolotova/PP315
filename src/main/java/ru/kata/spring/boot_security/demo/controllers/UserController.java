package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

@Controller
@RequestMapping("/user")
public class UserController {
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user_th) {
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user_th", user_th);
        return "user";
    }

}
