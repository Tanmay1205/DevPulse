package com.tanmay.devpulse.controller;

import com.tanmay.devpulse.dto.LoginRequest;
import com.tanmay.devpulse.dto.LoginResponse;
import com.tanmay.devpulse.dto.RegisterRequest;
import com.tanmay.devpulse.dto.RegisterResponse;
import com.tanmay.devpulse.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {

        return authService.register(request);

    }
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        return authService.login(request);

    }
}