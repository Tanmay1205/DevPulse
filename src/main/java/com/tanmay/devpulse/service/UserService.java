package com.tanmay.devpulse.service;

import com.tanmay.devpulse.dto.UpdateProfileRequest;
import com.tanmay.devpulse.dto.UserProfileResponse;
import com.tanmay.devpulse.entity.User;
import com.tanmay.devpulse.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    private final RefreshTokenService refreshTokenService;
    private final FileStorageService fileStorageService;

    public UserService(UserRepository userRepository,
                       CurrentUserService currentUserService,
                       RefreshTokenService refreshTokenService,
                       FileStorageService fileStorageService) {

        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
        this.refreshTokenService = refreshTokenService;
        this.fileStorageService = fileStorageService;
    }

    public UserProfileResponse getProfile() {

        User user = currentUserService.getCurrentUser();

        return mapToResponse(user);
    }

    public UserProfileResponse updateProfile(UpdateProfileRequest request) {

        User user = currentUserService.getCurrentUser();

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new IllegalArgumentException("Email already exists");
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        logger.info("Profile updated for {}", user.getEmail());

        return mapToResponse(user);
    }

    public void deleteAccount() {

        User user = currentUserService.getCurrentUser();

        refreshTokenService.revokeRefreshToken(user);

        userRepository.delete(user);

        logger.info("Account deleted for {}", user.getEmail());
    }

    private UserProfileResponse mapToResponse(User user) {

        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getProfileImage() == null
                        ? null
                        : "http://localhost:8080/uploads/" + user.getProfileImage(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserProfileResponse uploadProfileImage(MultipartFile file) {

        User user = currentUserService.getCurrentUser();

        if (user.getProfileImage() != null &&
                !user.getProfileImage().isBlank()) {

            fileStorageService.deleteFile(user.getProfileImage());
        }

        String fileName = fileStorageService.storeFile(file);

        user.setProfileImage(fileName);

        userRepository.save(user);

        logger.info("Profile image updated for {}", user.getEmail());

        return mapToResponse(user);
    }
}