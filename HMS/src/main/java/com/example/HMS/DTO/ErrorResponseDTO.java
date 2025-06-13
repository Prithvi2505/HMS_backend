package com.example.HMS.DTO;

import java.util.Date;

public class ErrorResponseDTO {
    private String message;
    private String details;
    private int status;

    public ErrorResponseDTO(String message, String details, int status) {
        this.message = message;
        this.details = details;
        this.status = status;
    }

    // Getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
