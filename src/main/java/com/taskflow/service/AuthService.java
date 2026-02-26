package com.taskflow.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.taskflow.dto.AuthResponse;
import com.taskflow.dto.LoginRequest;
import com.taskflow.dto.RegisterRequest;
import com.taskflow.entity.User;
import com.taskflow.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // REGISTER
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword()); // plain text (OK for now)
        user.setCreatedAt(LocalDateTime.now());      // 🔥 IMPORTANT

        userRepository.save(user);

        return new AuthResponse("REGISTER_SUCCESS");
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password"));

        if (!user.getPasswordHash().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new AuthResponse("LOGIN_SUCCESS");
    }
}