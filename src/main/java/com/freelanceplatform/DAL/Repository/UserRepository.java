package com.freelanceplatform.DAL.Repository;

import com.freelanceplatform.DAL.Entity.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(@NotNull @NotEmpty String email);
}
