package com.example.flightapi.model.Entity;

import java.time.LocalDateTime;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
//import lombok.Getter;
import lombok.Generated;
import lombok.NoArgsConstructor;
//import lombok.Setter;

@Entity
@Table(
        name = "Users"
)
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long userId;
    @Column(
            nullable = false,
            unique = true,
            length = 50
    )
    private String username;
    @Column(
            nullable = false,
            length = 255
    )
    private String passwordHash;
    @Column(
            nullable = false,
            unique = true,
            length = 100
    )
    private String email;
    @Column(
            nullable = false,
            length = 50
    )
    private String firstName;
    @Column(
            nullable = false,
            length = 50
    )
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private UserRole role;
    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getRole() {
        return this.role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Generated
    public User() {
        this.role = UserRole.USER;
        this.createdAt = LocalDateTime.now();
    }

    @Generated
    public User(final Long userId, final String username, final String passwordHash, final String email, final String firstName, final String lastName, final UserRole role, final LocalDateTime createdAt) {
        this.role = UserRole.USER;
        this.createdAt = LocalDateTime.now();
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.createdAt = createdAt;
    }
}
