package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.validator.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    private final UserValidator userValidator;

    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
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
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) return "newUser";
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id).orElse(new User()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute ("user") @Valid User user,
                             BindingResult bindingResult,
                             @PathVariable("id") Long id) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) return "newUser";
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
