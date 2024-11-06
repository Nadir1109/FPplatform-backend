package com.freelanceplatform.controller;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.CreateUserDTO;
import com.freelanceplatform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            User registeredUser = userService.registerUser(createUserDTO);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            // Als er bijvoorbeeld al een gebruiker met dit e-mailadres is
            return ResponseEntity.badRequest().body(null);
        }
    }
}
