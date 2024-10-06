package com.freelanceplatform.freelanceplatform.DAL.Interface;

import com.freelanceplatform.freelanceplatform.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAL extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
