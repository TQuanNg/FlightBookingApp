//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.flightapi.service;

import com.example.flightapi.model.DTO.UserDTO;
import com.example.flightapi.model.Entity.User;
import com.example.flightapi.model.Entity.UserRole;
import com.example.flightapi.repository.UserRepository;
import com.example.flightapi.util.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiResponse registerUser(User user) {
        if (this.userRepository.existsByUsername(user.getUsername())) {
            System.out.println("Username already taken: " + user.getUsername());
            return new ApiResponse(false, "Username is already taken.");
        } else if (this.userRepository.existsByEmail(user.getEmail())) {
            System.out.println("Email already taken: " + user.getEmail());
            return new ApiResponse(false, "Email is already in use.");
        } else {
            user.setRole(UserRole.USER);
            this.userRepository.save(user);
            return new ApiResponse(true, "User registered successfully.");
        }
    }

    public ApiResponse loginUser(String username, String passwordHash) {
        Optional<User> existingUser = this.userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            User user = (User)existingUser.get();
            if (user.getPasswordHash().equals(passwordHash)) {
                String token = JwtUtil.generateToken(username, user.getRole().name());
                UserDTO userDTO = this.getUserDetails(username);
                if (userDTO == null) {
                    return new ApiResponse(false, "User details not found after successful login.", (Object)null);
                } else {
                    Map<String, Object> responseData = new HashMap();
                    responseData.put("token", token);
                    responseData.put("user", userDTO);
                    responseData.put("role", user.getRole().name());
                    return new ApiResponse(true, "Login successful!", responseData);
                }
            } else {
                return new ApiResponse(false, "Invalid password.");
            }
        } else {
            return new ApiResponse(false, "User not found.");
        }
    }

    public UserDTO getUserDetails(String username) {
        Optional<User> existingUser = this.userRepository.findByUsername(username);
        User user = (User)existingUser.get();
        if (user == null) {
            System.out.println("User is null?? " + username);
            return null;
        } else {
            return new UserDTO(user.getUserId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
        }
    }
}
