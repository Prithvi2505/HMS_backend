package com.example.HMS.DTO;

import jakarta.validation.constraints.NotBlank;

public class StaffUpdateRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Type is required")
    private String type;

    public StaffUpdateRequestDTO(String name, String gender, String type) {
        this.name = name;
        this.gender = gender;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
