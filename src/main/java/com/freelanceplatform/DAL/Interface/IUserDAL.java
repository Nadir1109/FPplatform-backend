package com.freelanceplatform.DAL.Interface;

import com.freelanceplatform.DAL.Entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAL {
    User save(User user);
    List<User> findAll();
    Optional<User> findByEmail(String email);

}
