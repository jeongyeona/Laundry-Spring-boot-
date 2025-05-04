// DTO: com.example.Laundry.dto.ReplyBoardResponseDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record ReplyBoardResponseDto(
        Integer rnum,
        Integer refNum,
        String writer,
        String content,
        LocalDate regdate,
        LocalDate updateDate
) {}
