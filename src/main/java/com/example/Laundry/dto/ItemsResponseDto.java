package com.example.Laundry.dto;

import java.math.BigDecimal;

/**
 * Items 조회/응답용 DTO
 */
public record ItemsResponseDto(
        Long inum,
        String category,
        String item,
        BigDecimal price
) {}
