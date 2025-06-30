package com.example.HMS.DTO;

public class DoctorNameIdDTO {
    private int id;
    private String name;

    public DoctorNameIdDTO() {
    }

    public DoctorNameIdDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
