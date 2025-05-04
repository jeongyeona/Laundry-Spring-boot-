// DTO: com.example.Laundry.dto.QnaBoardResponseDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record QnaBoardResponseDto(
        Integer num,
        String writer,
        String title,
        String content,
        LocalDate regdate,
        String orgFileName,
        String saveFileName,
        Integer fileSize,
        Integer checkReply
) {}