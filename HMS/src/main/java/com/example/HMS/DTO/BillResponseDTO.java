package com.example.HMS.DTO;

import java.util.Date;

public class BillResponseDTO {
    private int id;
    private int amount;
    private String status;
    private Date date;
    private String billDetail;
    private int patientId;

    public BillResponseDTO() {
    }

    public BillResponseDTO(int id, int amount, Date date, String billDetail, int patientId, String status) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.billDetail = billDetail;
        this.patientId = patientId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBillDetail() {
        return billDetail;
    }

    public void setBillDetail(String billDetail) {
        this.billDetail = billDetail;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
