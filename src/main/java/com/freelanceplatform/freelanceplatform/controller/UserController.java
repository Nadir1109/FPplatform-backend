package com.freelanceplatform.freelanceplatform.controller;

import com.freelanceplatform.freelanceplatform.Logic.UserLogic;
import com.freelanceplatform.freelanceplatform.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserLogic userLogic;

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users user = userLogic.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userLogic.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        Users newUser = userLogic.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users user) {
        Users updatedUser = userLogic.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userLogic.deleteUser(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
