package com.freelanceplatform.Service;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IUserDAL;
import com.freelanceplatform.DAL.Mapper.UserMapper;
import com.freelanceplatform.DTO.CreateUserDTO;
import com.freelanceplatform.DTO.UserDTO;
import com.freelanceplatform.DTO.UserLoginDTO;
import com.freelanceplatform.DTO.UserRole;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final IUserDAL userDAL;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(IUserDAL userDAL, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userDAL = userDAL;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDTO registerUser(CreateUserDTO createUserDTO) {
        if (userDAL.findByEmail(createUserDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        var user = userMapper.toEntity(createUserDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        var savedUser = userDAL.save(user);
        return userMapper.toDTO(savedUser);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userDAL.findByEmail(email).map(userMapper::toDTO);
    }
    public List<UserDTO> getAllUsers() {
        return userDAL.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO loginUser(UserLoginDTO userLoginDTO) {
        var user = userDAL.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return userMapper.toDTO(user);
    }
}
