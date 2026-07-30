package com.tanmay.devpulse.controller;

import com.tanmay.devpulse.dto.UpdateProfileRequest;
import com.tanmay.devpulse.dto.UserProfileResponse;
import com.tanmay.devpulse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(
                userService.updateProfile(request)
        );
    }

    @PostMapping("/profile/image")
    public ResponseEntity<UserProfileResponse> uploadProfileImage(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                userService.uploadProfileImage(file)
        );
    }

    @DeleteMapping("/account")
    public ResponseEntity<String> deleteAccount() {

        userService.deleteAccount();

        return ResponseEntity.ok("Account deleted successfully");
    }
}