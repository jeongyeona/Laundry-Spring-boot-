// Mapper: com.example.Laundry.mapper.QnaBoardMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.QnaBoard;
import com.example.Laundry.dto.QnaBoardCreateDto;
import com.example.Laundry.dto.QnaBoardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QnaBoardMapper {
    QnaBoardMapper INSTANCE = Mappers.getMapper(QnaBoardMapper.class);

    @Mapping(target = "num", ignore = true)
    QnaBoard toEntity(QnaBoardCreateDto dto);

    QnaBoardResponseDto toDto(QnaBoard entity);
}