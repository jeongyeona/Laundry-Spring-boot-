// DTO: com.example.Laundry.dto.FaqBoardResponseDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record FaqBoardResponseDto(
        Integer num,
        String category,
        String writer,
        String title,
        String content,
        LocalDate regdate,
        LocalDate updateDate
) {}