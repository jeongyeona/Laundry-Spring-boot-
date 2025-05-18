package com.example.Laundry.dto;

import com.example.Laundry.domain.Items;
import com.example.Laundry.domain.ServiceOrder;

import java.math.BigDecimal;

/**
 * Items 조회/응답용 DTO
 */
public record ItemsResponseDto(
        int inum,
        String category,
        String item,
        BigDecimal price
) {
    /** ServiceOrder 엔티티를 DTO로 변환하는 생성자 */
    public ItemsResponseDto(Items order) {
        this(
            order.getInum(),
            order.getCategory(),
            order.getItem(),
            order.getPrice()
        );
    }

}
