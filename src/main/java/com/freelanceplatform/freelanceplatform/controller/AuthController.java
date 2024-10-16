package com.freelanceplatform.freelanceplatform.controller;

import com.freelanceplatform.freelanceplatform.Logic.UserLogic;
import com.freelanceplatform.freelanceplatform.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserLogic userLogic;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        boolean isAuthenticated = userLogic.authenticate(user.getEmail(), user.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
