package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleDAOService;
import ru.kata.spring.boot_security.demo.service.UserDAOServiceImp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserDAOServiceImp userDAOServiceImp;
    private final RoleDAOService roleDAOService;

    public AdminController(UserDAOServiceImp userDAOServiceImp, RoleDAOService roleDAOService) {
        this.userDAOServiceImp = userDAOServiceImp;
        this.roleDAOService = roleDAOService;
    }

    @GetMapping()
    public String adminPage() {
        return "adminPage";
    }

    @GetMapping("/Rest")
    @ResponseBody
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userDAOServiceImp.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/principal")
    @ResponseBody
    public ResponseEntity<User> getPrincipal(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    @ResponseBody
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userDAOServiceImp.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/roles")
    @ResponseBody
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleDAOService.getAllRoles(), HttpStatus.OK);
    }


    @PatchMapping(value = "/edit/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        HashSet<Role> roles = new HashSet<>();
        Set<Role> selectRoles = user.getRoles();
        if (selectRoles != null && !selectRoles.isEmpty()) {
            for (Role name : selectRoles) {
                roles.add(roleDAOService.getRoleByName(name.toString()));
            }
        }
        user.setRoles(roles);
        userDAOServiceImp.updateUser(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<HttpStatus> save(@RequestBody User user) {
        HashSet<Role> roles = new HashSet<>();
        Set<Role> selectRoles = user.getRoles();
        if (selectRoles != null && !selectRoles.isEmpty()) {
            for (Role name : selectRoles) {
                roles.add(roleDAOService.getRoleByName(name.toString()));
            }
        }
        user.setRoles(roles);
        userDAOServiceImp.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        userDAOServiceImp.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
