// Mapper: com.example.Laundry.mapper.ReplyBoardMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.ReplyBoard;
import com.example.Laundry.dto.ReplyBoardCreateDto;
import com.example.Laundry.dto.ReplyBoardResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReplyBoardMapper {
    ReplyBoardMapper INSTANCE = Mappers.getMapper(ReplyBoardMapper.class);

    @Mapping(target = "rnum", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    ReplyBoard toEntity(ReplyBoardCreateDto dto);

    ReplyBoardResponseDto toDto(ReplyBoard entity);
}
