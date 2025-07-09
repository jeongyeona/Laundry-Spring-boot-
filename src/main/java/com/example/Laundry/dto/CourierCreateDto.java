package com.example.Laundry.dto;

import jakarta.validation.constraints.NotBlank;

public class CourierCreateDto {

    @NotBlank(message = "택배사 코드는 필수입니다.")
    private String courierCode;

    @NotBlank(message = "택배사 이름은 필수입니다.")
    private String courierName;

    private Boolean enabled = true;

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
