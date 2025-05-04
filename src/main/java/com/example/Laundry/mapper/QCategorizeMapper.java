// Mapper: com.example.Laundry.mapper.QCategorizeMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.QCategorize;
import com.example.Laundry.dto.QCategorizeCreateDto;
import com.example.Laundry.dto.QCategorizeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QCategorizeMapper {
    QCategorizeMapper INSTANCE = Mappers.getMapper(QCategorizeMapper.class);

    @Mapping(target = "category", ignore = false)
    QCategorize toEntity(QCategorizeCreateDto dto);

    QCategorizeResponseDto toDto(QCategorize entity);
}