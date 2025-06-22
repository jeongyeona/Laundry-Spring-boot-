package com.example.Laundry.service;

import com.example.Laundry.domain.PaymentLog;
import com.example.Laundry.dto.PaymentResponseDto;
import com.example.Laundry.repository.PaymentLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentLogRepository paymentLogRepository;

    public PaymentService(PaymentLogRepository repo) {
        this.paymentLogRepository = repo;
    }

    public void saveFailLog(String impUid, String merchantUid, String errorMsg) {
        PaymentLog log = new PaymentLog();
        log.setImpUid(impUid);
        log.setMerchantUid(merchantUid);
        log.setStatus("fail");
        log.setMessage("결제 실패: " + errorMsg);
        paymentLogRepository.save(log);
    }

    public PaymentResponseDto saveSuccessLog(String impUid, String merchantUid) {
        PaymentLog log = new PaymentLog();
        log.setImpUid(impUid);
        log.setMerchantUid(merchantUid);
        log.setStatus("success");
        log.setMessage("결제 성공");
        log.setCreatedAt(LocalDateTime.now());
        paymentLogRepository.save(log);

        return new PaymentResponseDto(
                log.getImpUid(),
                log.getMerchantUid(),
                log.getStatus(),
                log.getMessage(),
                log.getCreatedAt()
        );
    }
}
