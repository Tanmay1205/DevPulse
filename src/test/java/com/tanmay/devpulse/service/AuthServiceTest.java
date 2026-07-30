package com.tanmay.devpulse.service;

import com.tanmay.devpulse.dto.LoginRequest;
import com.tanmay.devpulse.dto.LoginResponse;
import com.tanmay.devpulse.dto.RegisterRequest;
import com.tanmay.devpulse.dto.RegisterResponse;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.enums.Role;
import com.tanmay.devpulse.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void testRegisterSuccess() {

        RegisterRequest request = new RegisterRequest();
        request.setName("Tanmay");
        request.setEmail("tanmay@gmail.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        RegisterResponse response = authService.register(request);

        assertEquals("User registered successfully", response.getMessage());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterDuplicateEmail() {

        RegisterRequest request = new RegisterRequest();
        request.setName("Tanmay");
        request.setEmail("tanmay@gmail.com");
        request.setPassword("password123");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.register(request)
        );

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {

        LoginRequest request = new LoginRequest();
        request.setEmail("tanmay@gmail.com");
        request.setPassword("password123");

        User user = new User();
        user.setEmail("tanmay@gmail.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password123", "encodedPassword"))
                .thenReturn(true);

        when(jwtService.generateToken(user.getEmail(), user.getRole().name()))
                .thenReturn("jwt-token");

        LoginResponse response = authService.login(request);

        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void testLoginInvalidEmail() {

        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@gmail.com");
        request.setPassword("password123");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    void testLoginWrongPassword() {

        LoginRequest request = new LoginRequest();
        request.setEmail("tanmay@gmail.com");
        request.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("tanmay@gmail.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.USER);

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrongpassword", "encodedPassword"))
                .thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid email or password", exception.getMessage());
    }
}