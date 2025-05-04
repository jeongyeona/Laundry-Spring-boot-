// DTO: com.example.Laundry.dto.ServiceOrderCreateDto.java
package com.example.Laundry.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ServiceOrderCreateDto(
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