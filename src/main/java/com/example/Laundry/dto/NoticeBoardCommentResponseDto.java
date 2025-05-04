// DTO: com.example.Laundry.dto.NoticeBoardCommentResponseDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record NoticeBoardCommentResponseDto(
        Integer num,
        String writer,
        String content,
        String targetId,
        Integer refGroup,
        Integer commentGroup,
        String deleted,
        LocalDate regdate
) {}