package com.example.Laundry.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Items 생성 요청 DTO
 */
public record ItemsCreateDto(
        @NotBlank(message = "카테고리는 필수 입력 항목입니다.")
        @Size(max = 20, message = "카테고리는 최대 50자입니다.")
        String category,

        @NotBlank(message = "아이템명은 필수 입력 항목입니다.")
        @Size(max = 100, message = "아이템명은 최대 100자입니다.")
        String item,

        @NotNull(message = "가격은 필수 입력 항목입니다.")
        @DecimalMin(value = "0.0", inclusive = false, message = "가격은 0보다 커야 합니다.")
        BigDecimal price
) {}