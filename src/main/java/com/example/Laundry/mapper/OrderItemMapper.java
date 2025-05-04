// Mapper: com.example.Laundry.mapper.OrderItemMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.OrderItem;
import com.example.Laundry.dto.OrderItemCreateDto;
import com.example.Laundry.dto.OrderItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(target = "num", ignore = true)
    OrderItem toEntity(OrderItemCreateDto dto);

    OrderItemResponseDto toDto(OrderItem entity);
}