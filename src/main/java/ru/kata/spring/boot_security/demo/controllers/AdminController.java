package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;


@Controller
@RequestMapping("/admin")
public class AdminController {
private final UserServiceImp userServiceImp;
private final RoleServiceImp roleServiceImp;


    public AdminController(UserServiceImp userServiceImp, RoleServiceImp roleServiceImp) {
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;
    }

    @GetMapping()
    public String adminPage(Model model, Authentication authentication) {
        model.addAttribute("user",authentication.getPrincipal());
        model.addAttribute("allUsers", userServiceImp.getAllUsers());
        model.addAttribute("allRoles",roleServiceImp.findAll());
        return "adminPage";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User user) {

        userServiceImp.updateUser(id,user);
        return "redirect:/admin";
    }

    @PostMapping()
    public String creatUser(@ModelAttribute("user") User user) {
        userServiceImp.saveUser(user);
        return "redirect:/admin";
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userServiceImp.deleteUser(id);
        return "redirect:/admin";
    }
}
