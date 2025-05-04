// DTO: com.example.Laundry.dto.ReviewBoardResponseDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record ReviewBoardResponseDto(
        Integer num,
        String writer,
        String title,
        String content,
        Integer viewCount,
        LocalDate regdate,
        Integer star
) {}