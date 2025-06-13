package com.example.HMS.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class BillRequestDTO {
    @Min(value = 0, message = "Amount must be positive")
    private int amount;

    @NotNull(message = "Date is required")
    private Date date;

    @NotBlank(message = "Bill detail is required")
    private String billDetail;

    public BillRequestDTO(int amount, Date date, String billDetail, int patientId) {
        this.amount = amount;
        this.date = date;
        this.billDetail = billDetail;
        this.patientId = patientId;
    }

    private int patientId;

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
}
