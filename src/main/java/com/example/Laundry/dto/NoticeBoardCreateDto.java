// DTO: com.example.Laundry.dto.NoticeBoardCreateDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record NoticeBoardCreateDto(
        String writer,
        String title,
        String content,
        Integer viewCount,
        LocalDate regdate
) {}