package com.freelanceplatform.Service;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IUserDAL;
import com.freelanceplatform.DTO.CreateUserDTO;
import com.freelanceplatform.DTO.UserLoginDTO;
import com.freelanceplatform.DTO.UserRole;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final IUserDAL userDAL;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(IUserDAL userDAL, BCryptPasswordEncoder passwordEncoder) {
        this.userDAL = userDAL;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(CreateUserDTO createUserDTO) {
        if (userDAL.findByEmail(createUserDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered");
        }

        String role = createUserDTO.getRole() != null ? String.valueOf(createUserDTO.getRole()) : "USER";

        User user = User.builder()
                .name(createUserDTO.getName())
                .email(createUserDTO.getEmail())
                .password(passwordEncoder.encode(createUserDTO.getPassword())) // Wachtwoord hashen
                .role(UserRole.valueOf(role.toUpperCase())) // Zet de rol consistent in hoofdletters
                .build();

        return userDAL.save(user);
    }

    public User loginUser(UserLoginDTO userLoginDTO) {
        User user = userDAL.findByEmail(userLoginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Plaats de gebruiker in de SecurityContext
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return user;
    }

    public Optional<User> getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            return userDAL.findByEmail(email);
        }

        return Optional.empty();
    }
}
