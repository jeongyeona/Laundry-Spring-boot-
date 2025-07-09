package com.example.Laundry.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "courier")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "courier_code", nullable = false, unique = true, length = 50)
    private String courierCode;

    @Column(name = "courier_name", nullable = false, length = 100)
    private String courierName;

    @Column(nullable = false)
    private Boolean enabled = true;

    public Courier() {}

    public Courier(String courierCode, String courierName, Boolean enabled) {
        this.courierCode = courierCode;
        this.courierName = courierName;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public String getCourierCode() {
        return courierCode;
    }

    public void setCourierCode(String courierCode) {
        this.courierCode = courierCode;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
