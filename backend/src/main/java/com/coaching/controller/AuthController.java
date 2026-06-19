package com.coaching.controller;

import com.coaching.dto.AuthRequest;
import com.coaching.dto.AuthResponse;
import com.coaching.dto.ApiResponse;
import com.coaching.model.User;
import com.coaching.security.JwtTokenProvider;
import com.coaching.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody AuthRequest authRequest) {
        try {
            User user = new User();
            user.setEmail(authRequest.getEmail());
            user.setPassword(authRequest.getPassword());
            user.setFirstName(authRequest.getFirstName());
            user.setLastName(authRequest.getLastName());
            user.setRole(User.UserRole.valueOf(authRequest.getRole() != null ? authRequest.getRole() : "STUDENT"));

            User registeredUser = userService.registerUser(user);
            String token = jwtTokenProvider.generateTokenFromUserId(registeredUser.getId(), registeredUser.getEmail());

            AuthResponse response = AuthResponse.builder()
                    .id(registeredUser.getId())
                    .email(registeredUser.getEmail())
                    .firstName(registeredUser.getFirstName())
                    .lastName(registeredUser.getLastName())
                    .role(registeredUser.getRole().toString())
                    .token(token)
                    .message("User registered successfully")
                    .build();

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("User registered successfully")
                            .data(response)
                            .statusCode(201)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .build()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            User user = userService.findByEmail(authRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            String token = jwtTokenProvider.generateTokenFromUserId(user.getId(), user.getEmail());

            AuthResponse response = AuthResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole().toString())
                    .token(token)
                    .message("Login successful")
                    .build();

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Login successful")
                            .data(response)
                            .statusCode(200)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .build()
            );
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getProfile(@RequestHeader("Authorization") String token) {
        try {
            String email = jwtTokenProvider.getEmailFromToken(token.replace("Bearer ", ""));
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(
                    ApiResponse.builder()
                            .success(true)
                            .message("Profile retrieved successfully")
                            .data(user)
                            .statusCode(200)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .statusCode(400)
                            .build()
            );
        }
    }
}
