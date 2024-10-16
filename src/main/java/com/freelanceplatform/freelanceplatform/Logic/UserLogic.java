package com.freelanceplatform.freelanceplatform.Logic;

import com.freelanceplatform.freelanceplatform.DAL.Interface.UserDAL;
import com.freelanceplatform.freelanceplatform.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLogic {

    @Autowired
    private UserDAL userDAL;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String username, String password) {
        Optional<Users> optionalUser = userDAL.findByUsername(username);

        // Controleer of de gebruiker bestaat
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            // Vergelijk het ingevoerde wachtwoord met het gehashte wachtwoord
            return passwordEncoder.matches(password, user.getPasswordHash());
        }

        // Return false als de gebruiker niet is gevonden of als het wachtwoord niet klopterfff
        return false;
    }
}
