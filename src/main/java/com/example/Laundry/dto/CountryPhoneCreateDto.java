package com.example.Laundry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CountryPhoneCreateDto(
        @NotBlank(message = "Country code must not be blank")
        String countryCode,

        @NotBlank(message = "Dial code must not be blank")
        String dialCode,

        @NotBlank(message = "Country name must not be blank")
        @Size(max = 100, message = "Country name too long")
        String countryName
) {}