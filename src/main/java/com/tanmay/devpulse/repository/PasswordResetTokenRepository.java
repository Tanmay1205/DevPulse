package com.tanmay.devpulse.repository;

import com.tanmay.devpulse.entity.PasswordResetToken;
import com.tanmay.devpulse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);

    void deleteByUser(User user);

    void deleteByToken(String token);
}