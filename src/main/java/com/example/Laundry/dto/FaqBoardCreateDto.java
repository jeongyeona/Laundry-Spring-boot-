// DTO: com.example.Laundry.dto.FaqBoardCreateDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FaqBoardCreateDto(
        String category,
        String writer,
        String title,
        String content,
        LocalDateTime regdate,
        LocalDateTime updatedate
) {}