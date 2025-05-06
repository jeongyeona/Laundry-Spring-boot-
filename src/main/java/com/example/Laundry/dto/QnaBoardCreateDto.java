// DTO: com.example.Laundry.dto.QnaBoardCreateDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record QnaBoardCreateDto(
        String writer,
        String title,
        String content,
        LocalDateTime regdate,
        String orgFileName,
        String saveFileName,
        Integer fileSize,
        Integer checkReply
) {}