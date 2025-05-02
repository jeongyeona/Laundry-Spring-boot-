package com.example.Laundry.dto;

import java.time.LocalDate;

/**
 * 회원 정보 조회/응답용 DTO
 */
public record UserResponseDto(
        String id,
        String name,
        String email,
        String addr,
        String phone,
        String countryCode,
        String dialCode,
        String manager,
        String profile,
        LocalDate regdate
) {}
