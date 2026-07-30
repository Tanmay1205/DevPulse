package com.tanmay.devpulse.service;

import com.tanmay.devpulse.dto.*;
import com.tanmay.devpulse.entity.RefreshToken;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.enums.Role;
import com.tanmay.devpulse.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tanmay.devpulse.dto.ForgotPasswordRequest;
import com.tanmay.devpulse.dto.ResetPasswordRequest;
import com.tanmay.devpulse.entity.PasswordResetToken;
import com.tanmay.devpulse.repository.PasswordResetTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final RefreshTokenService refreshTokenService;
    private final CurrentUserService currentUserService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       RefreshTokenService refreshTokenService,
                       CurrentUserService currentUserService,
                       PasswordResetTokenRepository passwordResetTokenRepository,
                       EmailService emailService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.currentUserService = currentUserService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    public RegisterResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return new RegisterResponse("User registered successfully");
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

// Temporary until RefreshTokenService is implemented
        String refreshToken = refreshTokenService
                .createRefreshToken(user)
                .getToken();

        logger.info("Login successful: {}", request.getEmail());

        return new LoginResponse(accessToken, refreshToken);
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new LoginResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    public void logout(RefreshTokenRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        refreshTokenService.revokeRefreshToken(refreshToken.getUser());
    }

    public void changePassword(ChangePasswordRequest request) {

        User user = currentUserService.getCurrentUser();

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new IllegalArgumentException(
                    "New password must be different from current password");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);

        logger.info("Password changed successfully for {}", user.getEmail());
    }

    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        passwordResetTokenRepository.deleteByUser(user);

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setUser(user);
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));

        passwordResetTokenRepository.save(resetToken);

        emailService.sendPasswordResetEmail(
                user.getEmail(),
                resetToken.getToken()
        );

        logger.info("Password reset token generated for {}", user.getEmail());
    }

    public void resetPassword(ResetPasswordRequest request) {

        PasswordResetToken resetToken =
                passwordResetTokenRepository.findByToken(request.getToken())
                        .orElseThrow(() ->
                                new IllegalArgumentException("Invalid reset token"));

        if (resetToken.isExpired()) {

            passwordResetTokenRepository.delete(resetToken);

            throw new IllegalArgumentException("Reset token expired");
        }

        User user = resetToken.getUser();

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword())
        );

        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);

        logger.info("Password reset successfully for {}", user.getEmail());
    }


}