// DTO: com.example.Laundry.dto.QnaBoardResponseDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record QnaBoardResponseDto(
        Integer num,
        String writer,
        String title,
        String content,
        LocalDateTime regdate,
        String orgFileName,
        String saveFileName,
        Integer fileSize,
        Integer checkReply
) {}