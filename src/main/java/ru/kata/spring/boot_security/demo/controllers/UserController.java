package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import ru.kata.spring.boot_security.demo.models.User;

@Controller
@RequestMapping("/user")
public class UserController {

   @GetMapping()
   public String userPage() {
      return "userPage";
   }

   @GetMapping("/principal")
   @ResponseBody
   public ResponseEntity<User> getPrincipal(Authentication authentication) {
      User user = (User) authentication.getPrincipal();
      return new ResponseEntity<>(user, HttpStatus.OK);
   }
}
