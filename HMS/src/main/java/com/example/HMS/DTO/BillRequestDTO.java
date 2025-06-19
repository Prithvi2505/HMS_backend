package com.example.HMS.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class BillRequestDTO {
    @Min(value = 0, message = "Amount must be positive")
    private int amount;

    @NotNull(message = "Date is required")
    private String date;

    @NotBlank(message = "Bill detail is required")
    private String billDetail;

    @NotBlank(message = "Status should not be blank")
    private String status;

    public BillRequestDTO(int amount, String date, String billDetail, int patientId, String status) {
        this.amount = amount;
        this.date = date;
        this.billDetail = billDetail;
        this.patientId = patientId;
        this.status = status;
    }

    private int patientId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
