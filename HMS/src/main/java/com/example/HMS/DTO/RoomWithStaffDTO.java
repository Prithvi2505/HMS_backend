package com.example.HMS.DTO;

public class RoomWithStaffDTO {
    private int roomId;
    private String type;
    private int capacity;
    private int price;
    private int staffId;

    public RoomWithStaffDTO(int roomId, String type, int capacity, int price, int staffId) {
        this.roomId = roomId;
        this.type = type;
        this.capacity = capacity;
        this.price = price;
        this.staffId = staffId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }
}
