// DTO: com.example.Laundry.dto.ReplyBoardCreateDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record ReplyBoardCreateDto(
        Integer refNum,
        String writer,
        String content,
        LocalDate regdate
) {}