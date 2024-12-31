package com.freelanceplatform.controller;

import com.freelanceplatform.DTO.UserDTO;
import com.freelanceplatform.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(Authentication authentication) {
        String currentRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("");

        if (!currentRole.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}