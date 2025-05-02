package com.example.Laundry.dto;

public record CountryPhoneResponseDto(
        String countryCode,
        String dialCode,
        String countryName
) {}
