package com.freelanceplatform.controller;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.CreateUserDTO;
import com.freelanceplatform.DTO.UserLoginDTO;
import com.freelanceplatform.Service.UserService;
import com.freelanceplatform.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            User registeredUser = userService.registerUser(createUserDTO);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            User loggedInUser = userService.loginUser(userLoginDTO);

            // Genereer JWT-token
            String token = jwtTokenProvider.createToken(loggedInUser.getEmail(), loggedInUser.getRole().name());

            // Stel de response samen
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("email", loggedInUser.getEmail());
            response.put("role", loggedInUser.getRole().name());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Login failed: " + e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser() {
        return userService.getAuthenticatedUser()
                .map(user -> {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("name", user.getName());
                    userInfo.put("email", user.getEmail());
                    userInfo.put("role", String.valueOf(user.getRole()));
                    return ResponseEntity.ok(userInfo);
                })
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Not logged in")));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out");
    }
}
