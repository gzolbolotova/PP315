package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String basePage(@ModelAttribute("newUser") User newUser, Model model,
                        @AuthenticationPrincipal User thisUser) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("thisUser", thisUser);
        return "index";
    }


    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        if (!userService.getAllUsername().contains(user.getUsername())) {
            userService.saveUser(user);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/admin";
    }


    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user_u") User user) {
        userService.saveUser(user);
        return "redirect:/admin";

    }
}
