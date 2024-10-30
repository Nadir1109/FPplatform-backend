package com.freelanceplatform.Service.Implementation;

import com.freelanceplatform.DAL.Custom.UserDAL;
import com.freelanceplatform.DAL.Repository.UserRepository;
import com.freelanceplatform.DTO.UserLoginDTO;
import com.freelanceplatform.DTO.UserRegisterDTO;
import com.freelanceplatform.DTO.UserUpdateDTO;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.Service.Interface.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserRegisterDTO userRegisterDTO) {
        User users = User.builder()
                .name(userRegisterDTO.getName())
                .email(userRegisterDTO.getEmail())
                .password(passwordService.hashPassword(userRegisterDTO.getPassword()))
                .build();
        return userRepository.save(users);
    }

    @Override
    public User updateUser(UserUpdateDTO userUpdateDTO) {
        Optional<User> existingUser = userRepository.findById(userUpdateDTO.getId());
        if (existingUser.isPresent()) {
            User users = existingUser.get();
            users.setName(userUpdateDTO.getName());
            users.setEmail(userUpdateDTO.getEmail());
            if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
                users.setPassword(passwordService.hashPassword(userUpdateDTO.getPassword()));
            }
            return userRepository.save(users);
        }
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean authenticate(UserLoginDTO userLoginDTO) {
        User users = userRepository.findByEmail(userLoginDTO.getEmail());
        return users != null && passwordService.checkPassword(userLoginDTO.getPassword(), users.getPassword());
    }
}
