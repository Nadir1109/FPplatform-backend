package com.freelanceplatform.freelanceplatform.DAL;

import com.freelanceplatform.freelanceplatform.model.jpa.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAL extends JpaRepository<Users, Long> {
    Users findByEmail(String email); // Extra methode voor authenticatie
}
