package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/newUser")
    public String getUsersAndRoles(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "newUser";
    }

    @PostMapping("/newUser")
    public String createUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id).orElse(new User()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
