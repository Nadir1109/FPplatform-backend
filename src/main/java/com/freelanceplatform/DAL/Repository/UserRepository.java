package com.freelanceplatform.DAL.Repository;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IUserDAL;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByEmail(String email);
}
