package com.freelanceplatform.freelanceplatform.service;

import com.freelanceplatform.freelanceplatform.DAL.UserDAL;
import com.freelanceplatform.freelanceplatform.model.dto.UserLoginDTO;
import com.freelanceplatform.freelanceplatform.model.dto.UserRegisterDTO;
import com.freelanceplatform.freelanceplatform.model.dto.UserUpdateDTO;
import com.freelanceplatform.freelanceplatform.model.jpa.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAL userDAL;
    private final PasswordService passwordService;

    public Users getUserById(Long id) {
        return userDAL.findById(id).orElse(null);
    }

    public List<Users> getAllUsers() {
        return userDAL.findAll();
    }

    public Users createUser(UserRegisterDTO userRegisterDTO) {
        Users users = Users.builder()
            .name(userRegisterDTO.getName())
            .email(userRegisterDTO.getEmail())
            .password(passwordService.hashPassword(userRegisterDTO.getPassword()))
            .build();
        return userDAL.save(users);
    }

    public Users updateUser(UserUpdateDTO userUpdateDTO) {
        return userDAL.findById(userUpdateDTO.getId()).map(existingUsers -> {
            existingUsers.setName(userUpdateDTO.getName());
            existingUsers.setEmail(userUpdateDTO.getEmail());
            if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
                existingUsers.setPassword(passwordService.hashPassword(userUpdateDTO.getPassword()));
            }
            return userDAL.save(existingUsers);
        }).orElse(null);
    }

    public boolean deleteUser(Long id) {
        if (userDAL.existsById(id)) {
            userDAL.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean authenticate(final UserLoginDTO userLoginDTO) {
        Users users = userDAL.findByEmail(userLoginDTO.getEmail());
        return users != null && passwordService.checkPassword(userLoginDTO.getPassword(), users.getPassword());
    }
}
