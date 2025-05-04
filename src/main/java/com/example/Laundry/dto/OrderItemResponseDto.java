// DTO: com.example.Laundry.dto.OrderItemResponseDto.java
package com.example.Laundry.dto;

public record OrderItemResponseDto(
        Integer num,
        Integer code,
        Integer inum,
        Integer count
) {}