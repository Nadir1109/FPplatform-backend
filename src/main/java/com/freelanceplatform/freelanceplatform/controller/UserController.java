package com.freelanceplatform.freelanceplatform.controller;

import com.freelanceplatform.freelanceplatform.DAL.Interface.UserDAL;
import com.freelanceplatform.freelanceplatform.Logic.UserLogic;
import com.freelanceplatform.freelanceplatform.model.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserDAL userDAL;
    private final UserLogic userLogic;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserDAL userDAL, UserLogic userLogic, PasswordEncoder passwordEncoder) {
        this.userDAL = userDAL;
        this.userLogic = userLogic;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userDAL.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Optional<Users> userOptional = userDAL.findById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Gebruikersnaam, e-mail en wachtwoord zijn verplicht.");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPasswordHash(hashedPassword);

        userDAL.save(user);
        return ResponseEntity.ok("Gebruiker succesvol geregistreerd!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        Optional<Users> existingUserOptional = userDAL.findById(id);

        if (existingUserOptional.isPresent()) {
            Users existingUser = existingUserOptional.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(updatedUser.getPassword());
                existingUser.setPasswordHash(hashedPassword);
            }

            userDAL.save(existingUser);
            return ResponseEntity.ok("Gebruiker succesvol bijgewerkt.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gebruiker niet gevonden.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Optional<Users> userOptional = userDAL.findById(id);

        if (userOptional.isPresent()) {
            userDAL.deleteById(id);
            return ResponseEntity.ok("Gebruiker succesvol verwijderd.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gebruiker niet gevonden.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        boolean isAuthenticated = userLogic.authenticate(user.getUsername(), user.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok("Inloggen succesvol voor: " + user.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ongeldige gebruikersnaam of wachtwoord.");
        }
    }
}
