// Mapper: com.example.Laundry.mapper.ServiceOrderMapper.java
package com.example.Laundry.mapper;

import com.example.Laundry.domain.ServiceOrder;
import com.example.Laundry.dto.ServiceOrderCreateDto;
import com.example.Laundry.dto.ServiceOrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceOrderMapper {
    ServiceOrderMapper INSTANCE = Mappers.getMapper(ServiceOrderMapper.class);

    @Mapping(target = "code", ignore = true)
    ServiceOrder toEntity(ServiceOrderCreateDto dto);

    ServiceOrderResponseDto toDto(ServiceOrder entity);
}