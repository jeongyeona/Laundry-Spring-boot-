package com.example.Laundry.mapper;

import com.example.Laundry.domain.Categorize;
import com.example.Laundry.dto.CategorizeCreateDto;
import com.example.Laundry.dto.CategorizeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategorizeMapper {

    CategorizeMapper INSTANCE = Mappers.getMapper(CategorizeMapper.class);

    Categorize toEntity(CategorizeCreateDto dto);

    CategorizeResponseDto toDto(Categorize entity);
}
