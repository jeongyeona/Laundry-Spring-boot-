// Mapper: com.example.Laundry.mapper.NoticeBoardCommentMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.NoticeBoardComment;
import com.example.Laundry.dto.NoticeBoardCommentCreateDto;
import com.example.Laundry.dto.NoticeBoardCommentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeBoardCommentMapper {
    NoticeBoardCommentMapper INSTANCE = Mappers.getMapper(NoticeBoardCommentMapper.class);

    @Mapping(target = "num", ignore = true)
    NoticeBoardComment toEntity(NoticeBoardCommentCreateDto dto);

    NoticeBoardCommentResponseDto toDto(NoticeBoardComment entity);
}