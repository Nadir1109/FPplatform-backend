package com.freelanceplatform.DAL.Implementation;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IUserDAL;
import com.freelanceplatform.DAL.Repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAL implements IUserDAL {
    private final UserRepository userRepository;

    public UserDAL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
