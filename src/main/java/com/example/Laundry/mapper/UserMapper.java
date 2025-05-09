package com.example.Laundry.mapper;

import com.example.Laundry.domain.User;
import com.example.Laundry.dto.UserCreateDto;
import com.example.Laundry.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "regdate", ignore = true)
    // 회원가입 요청 DTO → Entity
    User toEntity(UserCreateDto dto);

    // Entity → API/화면 반환용 DTO
    UserResponseDto toDto(User entity);
}
