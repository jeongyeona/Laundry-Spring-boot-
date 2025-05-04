// Mapper: com.example.Laundry.mapper.ReviewBoardMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.ReviewBoard;
import com.example.Laundry.dto.ReviewBoardCreateDto;
import com.example.Laundry.dto.ReviewBoardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewBoardMapper {
    ReviewBoardMapper INSTANCE = Mappers.getMapper(ReviewBoardMapper.class);

    @Mapping(target = "num", ignore = true)
    ReviewBoard toEntity(ReviewBoardCreateDto dto);

    ReviewBoardResponseDto toDto(ReviewBoard entity);
}