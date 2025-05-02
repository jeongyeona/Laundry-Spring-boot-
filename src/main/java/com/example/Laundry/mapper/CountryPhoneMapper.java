package com.example.Laundry.mapper;

import com.example.Laundry.domain.CountryPhone;
import com.example.Laundry.dto.CountryPhoneCreateDto;
import com.example.Laundry.dto.CountryPhoneResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryPhoneMapper {
    CountryPhone toEntity(CountryPhoneCreateDto dto);
    CountryPhoneResponseDto toDto(CountryPhone entity);
}