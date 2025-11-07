package com.example.flightapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.flightapi.service.UserService;
import com.example.flightapi.util.JwtUtil;
import com.example.flightapi.model.DTO.UserDTO;
import com.example.flightapi.model.Entity.User;
import com.example.flightapi.service.ApiResponse; 

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody User user) {
        ApiResponse response = userService.registerUser(user);
        
        if (response.isSuccess()) {
            System.out.println("User registered successfully.");
            return new ResponseEntity<>(response, HttpStatus.CREATED);  // Success: 201 Created
        } else {
            System.out.println("Registration failed: " + response.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  // Failure: 400 Bad Request
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestParam String username, @RequestParam String passwordHash) {
        ApiResponse response = userService.loginUser(username, passwordHash);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/me")
    public ResponseEntity<?> getCurrentUserInfo(@RequestHeader("Authorization") String token ) {
        String extractedToken = token.replace("Bearer ", "");
        String username = JwtUtil.extractUsername(extractedToken);

        UserDTO userDTO = userService.getUserDetails(username);

        if(userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    
        return ResponseEntity.ok(userDTO);
    }
}
