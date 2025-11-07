package com.example.flightapi.controller;

import com.example.flightapi.model.DTO.UserDTO;
import com.example.flightapi.model.Entity.User;
import com.example.flightapi.service.ApiResponse;
import com.example.flightapi.service.UserService;
import com.example.flightapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/users"})
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"/register"})
    public ResponseEntity<ApiResponse> registerUser(@RequestBody User user) {
        ApiResponse response = this.userService.registerUser(user);
        if (response.isSuccess()) {
            System.out.println("User registered successfully.");
            return new ResponseEntity(response, HttpStatus.CREATED);
        } else {
            System.out.println("Registration failed: " + response.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping({"/login"})
    public ResponseEntity<ApiResponse> loginUser(@RequestParam String username, @RequestParam String passwordHash) {
        ApiResponse response = this.userService.loginUser(username, passwordHash);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping({"/me"})
    public ResponseEntity<?> getCurrentUserInfo(@RequestHeader("Authorization") String token) {
        String extractedToken = token.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(extractedToken);
        UserDTO userDTO = this.userService.getUserDetails(username);
        return userDTO == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found") : ResponseEntity.ok(userDTO);
    }
}

