package com.example.Laundry.dto;

import java.time.LocalDateTime;

public class PaymentResponseDto {

    private String impUid;
    private String merchantUid;
    private String status;
    private String message;
    private LocalDateTime createdAt;

    public PaymentResponseDto() {}

    public PaymentResponseDto(String impUid, String merchantUid, String status, String message, LocalDateTime createdAt) {
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.status = status;
        this.message = message;
        this.createdAt = createdAt;
    }

    // getter/setter
    public String getImpUid() { return impUid; }
    public void setImpUid(String impUid) { this.impUid = impUid; }

    public String getMerchantUid() { return merchantUid; }
    public void setMerchantUid(String merchantUid) { this.merchantUid = merchantUid; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}