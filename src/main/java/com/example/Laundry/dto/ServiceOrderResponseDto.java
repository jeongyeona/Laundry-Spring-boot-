// DTO: com.example.Laundry.dto.ServiceOrderResponseDto.java
package com.example.Laundry.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ServiceOrderResponseDto(
        Integer code,
        String orderer,
        String category,
        BigDecimal orderPrice,
        String orderAddr,
        LocalDate regdate,
        String reservationDate,
        String request,
        String state,
        Integer getInvoiceNum,
        Integer sendInvoiceNum
) {}