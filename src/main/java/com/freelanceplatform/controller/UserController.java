package com.freelanceplatform.controller;

import com.freelanceplatform.DTO.UserRegisterDTO;
import com.freelanceplatform.DTO.UserUpdateDTO;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.Service.Interface.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User users = userService.getUserById(id);
        if (users == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @NotNull @Valid UserRegisterDTO userRegisterDTO) {
        User newUsers = userService.createUser(userRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUsers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody @NotNull @Valid UserUpdateDTO userUpdateDTO) {
        User updatedUsers = userService.updateUser(userUpdateDTO);
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
