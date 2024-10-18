package com.freelanceplatform.freelanceplatform.controller;

import com.freelanceplatform.freelanceplatform.model.dto.UserRegisterDTO;
import com.freelanceplatform.freelanceplatform.model.dto.UserUpdateDTO;
import com.freelanceplatform.freelanceplatform.model.jpa.Users;
import com.freelanceplatform.freelanceplatform.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users users = userService.getUserById(id);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody @NotNull @Valid UserRegisterDTO userRegisterDTO) {
        Users newUsers = userService.createUser(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUsers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody @NotNull @Valid UserUpdateDTO userUpdateDTO) {
        Users updatedUsers = userService.updateUser(userUpdateDTO);
        if (updatedUsers == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUsers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
