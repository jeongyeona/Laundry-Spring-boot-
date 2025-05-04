// DTO: com.example.Laundry.dto.NoticeBoardCommentCreateDto.java
package com.example.Laundry.dto;

import java.time.LocalDate;

public record NoticeBoardCommentCreateDto(
        String writer,
        String content,
        String targetId,
        Integer refGroup,
        Integer commentGroup,
        String deleted,
        LocalDate regdate
) {}