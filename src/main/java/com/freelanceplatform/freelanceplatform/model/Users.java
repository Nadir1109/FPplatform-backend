package com.freelanceplatform.freelanceplatform.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    @Transient
    private String password;
    private String passwordHash;

    // Constructor met parameters
    public Users(Long id, String username, String email, String passwordHash) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Lege constructor nodig voor JPA
    public Users() {

    }
}
