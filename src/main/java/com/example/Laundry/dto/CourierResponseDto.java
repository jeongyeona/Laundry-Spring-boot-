package com.example.Laundry.dto;

import com.example.Laundry.domain.Courier;

public class CourierResponseDto {

    private Long  id;
    private String courierCode;
    private String courierName;
    private Boolean enabled;

    // --- Constructor using Entity ---

    public CourierResponseDto(Courier courier) {
        this.id = courier.getId();
        this.courierCode = courier.getCourierCode();
        this.courierName = courier.getCourierName();
        this.enabled = courier.getEnabled();
    }

    // --- Getters ---

    public Long  getId() {
        return id;
    }

    public String getCourierCode() {
        return courierCode;
    }

    public String getCourierName() {
        return courierName;
    }

    public Boolean getEnabled() {
        return enabled;
    }
}
