// Mapper: com.example.Laundry.mapper.FaqBoardMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.FaqBoard;
import com.example.Laundry.dto.FaqBoardCreateDto;
import com.example.Laundry.dto.FaqBoardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FaqBoardMapper {
    FaqBoardMapper INSTANCE = Mappers.getMapper(FaqBoardMapper.class);

    @Mapping(target = "num", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    FaqBoard toEntity(FaqBoardCreateDto dto);

    FaqBoardResponseDto toDto(FaqBoard entity);
}