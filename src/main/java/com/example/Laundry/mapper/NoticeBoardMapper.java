// Mapper: com.example.Laundry.mapper.NoticeBoardMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.NoticeBoard;
import com.example.Laundry.dto.NoticeBoardCreateDto;
import com.example.Laundry.dto.NoticeBoardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoticeBoardMapper {
    NoticeBoardMapper INSTANCE = Mappers.getMapper(NoticeBoardMapper.class);

    @Mapping(target = "num", ignore = true)
    NoticeBoard toEntity(NoticeBoardCreateDto dto);

    NoticeBoardResponseDto toDto(NoticeBoard entity);
}