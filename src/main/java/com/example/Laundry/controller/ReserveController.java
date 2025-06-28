package com.example.Laundry.controller;

import com.example.Laundry.domain.PaymentLog;
import com.example.Laundry.dto.PaymentCreateDto;
import com.example.Laundry.dto.PaymentResponseDto;
import com.example.Laundry.repository.PaymentLogRepository;
import com.example.Laundry.service.PaymentService;
import com.example.Laundry.service.ServiceOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/Reserve")
public class ReserveController {

    private final PaymentService paymentService;
    private final ServiceOrderService service;

    public ReserveController(PaymentService  paymentService, ServiceOrderService service) {
        this.paymentService = paymentService;
        this.service = service;
    }

    @PostMapping("/cardPayment")
    public ResponseEntity<PaymentResponseDto> cardPayment(@RequestBody PaymentCreateDto dto) {
        try {
            PaymentResponseDto response = paymentService.saveSuccessLog(dto.getImpUid(), dto.getMerchantUid());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            paymentService.saveFailLog(dto.getImpUid(), dto.getMerchantUid(), e.getMessage());

            PaymentResponseDto failResponse = new PaymentResponseDto(
                    dto.getImpUid(),
                    dto.getMerchantUid(),
                    "fail",
                    "결제 실패: " + e.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(failResponse);
        }
    }
}
