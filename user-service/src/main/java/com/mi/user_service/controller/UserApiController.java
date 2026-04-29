package com.mi.user_service.controller;

import com.mi.user_service.model.AuthRequest;
import com.mi.user_service.model.LoginResponse;
import com.mi.user_service.model.SignUpResponse;
import com.mi.user_service.model.User;
import com.mi.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody AuthRequest request) {
        log.info("Request received to signup user with email: {}", request.email());
        try {
            User user = userService.signup(request.email(), request.password());
            return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResponse("Signup successful"));
        } catch (IllegalArgumentException e) {
            log.warn("Signup failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new SignUpResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthRequest request) {
        log.info("Request received to login user with email: {}", request.email());
        try {
            User user = userService.login(request.email(), request.password());
            return ResponseEntity.ok(new LoginResponse("Login successful"));
        } catch (IllegalArgumentException e) {
            log.warn("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(e.getMessage()));
        }
    }
}
