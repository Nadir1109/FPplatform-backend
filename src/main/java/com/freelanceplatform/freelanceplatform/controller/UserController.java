package com.freelanceplatform.freelanceplatform.controller;

import com.freelanceplatform.freelanceplatform.model.Users;
import com.freelanceplatform.freelanceplatform.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Haal alle gebruikers op
    @GetMapping
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    // Registreer een nieuwe gebruiker
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        System.out.println("Gebruiker aan het registeren: " + user.getUsername());
        if (user.getUsername() == null || user.getEmail() == null) {
            return ResponseEntity.badRequest().body("Gebruikersnaam en e-mail zijn verplicht.");
        }
        userRepository.save(user);
        System.out.println("Gebruiker succesvol opgeslagen: " + user.getUsername());
        return ResponseEntity.ok("Gebruiker succesvol geregistreerd!");
    }

    // Werk een bestaande gebruiker bij
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        Optional<Users> userData = userRepository.findById(id);

        if (userData.isPresent()) {
            Users user = userData.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            userRepository.save(user);
            return ResponseEntity.ok("Gebruiker succesvol bijgewerkt!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gebruiker niet gevonden");
        }
    }

    // Verwijder een gebruiker
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("Gebruiker succesvol verwijderd!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gebruiker niet gevonden");
        }
    }
}
