package com.example.Laundry.mapper;

import org.mapstruct.Mapper;
import com.example.Laundry.domain.Items;
import com.example.Laundry.dto.ItemsCreateDto;
import com.example.Laundry.dto.ItemsResponseDto;

/**
 * Items 엔티티와 DTO 간 변환을 담당하는 Mapper
 */
@Mapper(componentModel = "spring")
public interface ItemsMapper {

    /**
     * 생성 요청 DTO -> 엔티티 변환
     */
    Items toEntity(ItemsCreateDto dto);

    /**
     * 엔티티 -> 응답용 DTO 변환
     */
    ItemsResponseDto toDto(Items entity);
}
