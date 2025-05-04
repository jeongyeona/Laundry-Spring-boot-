// DTO: com.example.Laundry.dto.QnaBoardCreateDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record QnaBoardCreateDto(
        String writer,
        String title,
        String content,
        LocalDate regdate,
        String orgFileName,
        String saveFileName,
        Integer fileSize,
        Integer checkReply
) {}