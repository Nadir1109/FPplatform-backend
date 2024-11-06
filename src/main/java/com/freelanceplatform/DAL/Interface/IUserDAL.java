package com.freelanceplatform.DAL.Interface;

import com.freelanceplatform.DAL.Entity.User;
import java.util.Optional;

public interface IUserDAL {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    void deleteById(Long id);
    boolean existsById(Long id);
}
