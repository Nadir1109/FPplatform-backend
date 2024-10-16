package com.freelanceplatform.freelanceplatform.Logic;

import com.freelanceplatform.freelanceplatform.DAL.UserDAL;
import com.freelanceplatform.freelanceplatform.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLogic {

    @Autowired
    private UserDAL userDAL;

    public Users getUserById(Long id) {
        return userDAL.findById(id).orElse(null);
    }

    public List<Users> getAllUsers() {
        return userDAL.findAll();
    }

    public Users createUser(Users user) {
        // Hash het wachtwoord voordat het wordt opgeslagen
        user.setPassword(hashPassword(user.getPassword()));
        return userDAL.save(user);
    }

    public Users updateUser(Long id, Users updatedUser) {
        return userDAL.findById(id).map(existingUser -> {
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            if (!updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(hashPassword(updatedUser.getPassword()));
            }
            return userDAL.save(existingUser);
        }).orElse(null);
    }

    public boolean deleteUser(Long id) {
        if (userDAL.existsById(id)) {
            userDAL.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean authenticate(String email, String rawPassword) {
        Users user = userDAL.findByEmail(email);
        return user != null && checkPassword(rawPassword, user.getPassword());
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean checkPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
