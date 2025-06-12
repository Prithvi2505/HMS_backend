package com.example.HMS.DTO;

public class LoginResponseDTO {
    private String token;
    private int userId;
    private String role;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, int userId, String role) {
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
