// DTO: com.example.Laundry.dto.FaqBoardResponseDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FaqBoardResponseDto(
        Integer num,
        String category,
        String writer,
        String title,
        String content,
        LocalDateTime regdate,
        LocalDateTime updateDate
) {}