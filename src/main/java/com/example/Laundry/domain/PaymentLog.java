package com.example.Laundry.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_log")
public class PaymentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imp_uid", nullable = false)
    private String impUid;

    @Column(name = "merchant_uid", nullable = false)
    private String merchantUid;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
