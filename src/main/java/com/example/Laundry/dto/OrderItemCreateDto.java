// DTO: com.example.Laundry.dto.OrderItemCreateDto.java
package com.example.Laundry.dto;

public record OrderItemCreateDto(
        Integer code,
        Integer inum,
        Integer count
) {}