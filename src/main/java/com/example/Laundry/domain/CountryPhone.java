package com.example.Laundry.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "country_phone")
public class CountryPhone {

    @Id
    @Column(name = "country_code", length = 2)
    private String countryCode;

    @Column(name = "dial_code", nullable = false, length = 10)
    private String dialCode;

    @Column(name = "country_name", nullable = false, length = 100)
    private String countryName;

    protected CountryPhone() {}

    public CountryPhone(String countryCode, String dialCode, String countryName) {
        this.countryCode = countryCode;
        this.dialCode     = dialCode;
        this.countryName  = countryName;
    }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getDialCode() { return dialCode; }
    public void setDialCode(String dialCode) { this.dialCode = dialCode; }
    public String getCountryName() { return countryName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
}
