package com.freelanceplatform.Service;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IUserDAL;
import com.freelanceplatform.DTO.CreateUserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final IUserDAL userDAL;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(IUserDAL userDAL,BCryptPasswordEncoder passwordEncoder) {
        this.userDAL = userDAL;
        this.passwordEncoder = passwordEncoder;
    }
    public User registerUser(CreateUserDTO createUserDTO) {
        if (userDAL.findByEmail(createUserDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = User.builder()
                .name(createUserDTO.getName())
                .email(createUserDTO.getEmail())
                .password(passwordEncoder.encode(createUserDTO.getPassword())) // Wachtwoord hashen
                .role(createUserDTO.getRole())
                .build();

        return userDAL.save(user);
    }
    public Optional<User> getUserById(Long id) {
        return userDAL.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userDAL.findByEmail(email);
    }

    public User updateUser(Long id, CreateUserDTO updatedUser) {
        if (userDAL.existsById(id)) {
            User user = userDAL.findById(id).get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Update wachtwoord
            user.setRole(updatedUser.getRole());
            return userDAL.save(user);
        }
        throw new IllegalArgumentException("User not found");
    }

    public void deleteUser(Long id) {
        if (userDAL.existsById(id)) {
            userDAL.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
