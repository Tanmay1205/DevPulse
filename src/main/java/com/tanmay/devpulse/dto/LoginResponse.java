package com.tanmay.devpulse.dto;

public class LoginResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";

    private String name;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(
            String accessToken,
            String refreshToken,
            String name,
            String role
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.name = name;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}