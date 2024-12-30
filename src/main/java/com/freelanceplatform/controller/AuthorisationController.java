package com.freelanceplatform.controller;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.*;
import com.freelanceplatform.Service.UserService;
import com.freelanceplatform.config.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthorisationController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthorisationController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        if (createUserDTO.getRole() != UserRole.CLIENT && createUserDTO.getRole() != UserRole.FREELANCER) {
            return ResponseEntity.badRequest().body(null);
        }

        try {

            UserDTO registeredUser = userService.registerUser(createUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            // Haal de gebruiker op als UserDTO
            UserDTO loggedInUser = userService.loginUser(userLoginDTO);

            // Genereer een token op basis van de UserDTO
            String token = jwtTokenProvider.createToken(loggedInUser.getEmail(), loggedInUser.getRole());

            // Stel een LoginResponseDTO samen
            LoginResponseDTO response = new LoginResponseDTO(token, loggedInUser.getEmail(), loggedInUser.getRole());

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(null);
        }
    }

}