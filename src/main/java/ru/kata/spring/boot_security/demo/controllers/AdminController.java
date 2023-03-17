package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
private final UserServiceImp userServiceImp;


    public AdminController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;

    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userServiceImp.getAllUsers());
        return "showAllUsers";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user",new User());
        return "newUser";
    }

    @PostMapping()
    public String creatUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "newUser";
        }
        userServiceImp.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {

        model.addAttribute("user",userServiceImp.getUserById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, @PathVariable("id") long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userServiceImp.updateUser(id,user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userServiceImp.deleteUser(id);
        return "redirect:/admin";
    }
}
