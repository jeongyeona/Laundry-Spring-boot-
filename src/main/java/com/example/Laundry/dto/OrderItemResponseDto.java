// DTO: com.example.Laundry.dto.OrderItemResponseDto.java
package com.example.Laundry.dto;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Integer num,
        Integer code,
        Integer inum,
        Integer count,
        String itemName,         // 추가: 상품 이름
        BigDecimal price
) {}