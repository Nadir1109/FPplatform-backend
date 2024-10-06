package com.example.auth;

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
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserLogic userLogic;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users) {
        boolean isAuthenticated = userLogic.authenticate(users.getUsername(), users.getPasswordHash());

        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
