// DTO: com.example.Laundry.dto.NoticeBoardResponseDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NoticeBoardResponseDto(
        Integer num,
        String writer,
        String title,
        String content,
        Integer viewCount,
        LocalDateTime regdate
) {}