package com.example.flightapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.flightapi.model.Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT * FROM users WHERE username = ?1",
            nativeQuery = true
    )
    Optional<User> findByUsername(String username);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT 1 FROM users WHERE username = ?1) THEN TRUE ELSE FALSE END",
            nativeQuery = true
    )
    boolean existsByUsername(String username);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT 1 FROM users WHERE email = ?1) THEN TRUE ELSE FALSE END",
            nativeQuery = true
    )
    boolean existsByEmail(String email);
}
