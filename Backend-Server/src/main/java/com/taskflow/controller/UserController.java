package com.taskflow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.taskflow.dto.UserResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // GET USER PROFILE (mock for now)
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {

        UserResponse user = new UserResponse(
        );

        return ResponseEntity.ok(user);
    }
}