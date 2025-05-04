// DTO: com.example.Laundry.dto.ReviewBoardCreateDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record ReviewBoardCreateDto(
        String writer,
        String title,
        String content,
        Integer viewCount,
        LocalDate regdate,
        Integer star
) {}