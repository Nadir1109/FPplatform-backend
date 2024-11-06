package com.freelanceplatform.Service;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IUserDAL;
import com.freelanceplatform.DTO.CreateUserDTO;
import com.freelanceplatform.DTO.UserLoginDTO;
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
    public User loginUser(UserLoginDTO userLoginDTO) {
        User user = userDAL.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return user;
    }
    }
