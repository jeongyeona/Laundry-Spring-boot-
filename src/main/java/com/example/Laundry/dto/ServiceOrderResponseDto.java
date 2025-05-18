// DTO: com.example.Laundry.dto.ServiceOrderResponseDto.java
package com.example.Laundry.dto;

import com.example.Laundry.domain.ServiceOrder;

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
) {
    /** ServiceOrder 엔티티를 DTO로 변환하는 생성자 */
    public ServiceOrderResponseDto(ServiceOrder order) {
        this(
                order.getCode(),
                order.getOrderer(),
                order.getCategory(),
                order.getOrderPrice(),
                order.getOrderAddr(),
                order.getRegdate(),
                order.getReservationDate().toString(), // 필요한 변환
                order.getRequest(),
                order.getState(),
                order.getGetInvoiceNum(),
                order.getSendInvoiceNum()
        );
    }
}