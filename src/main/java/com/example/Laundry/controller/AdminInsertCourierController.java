package com.example.Laundry.controller;

import com.example.Laundry.service.ServiceOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/LoginInfo/Mypage")
public class AdminInsertCourierController {

    private final ServiceOrderService serviceOrderService;

    public AdminInsertCourierController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @PostMapping("/AdminInsertCourier")
    public ResponseEntity<Map<String, Object>> insertCourier(
            @RequestParam("courier") String courier,
            @RequestParam("codes") List<Integer> codes
    ) {
        // 송장번호 발급 + courier 저장 로직 호출
        int updatedCount = serviceOrderService.insertCourierAndInvoice(codes, courier);

        Map<String, Object> result = new HashMap<>();
        result.put("isSuccess", updatedCount > 0);
        result.put("count", updatedCount);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/AdminInsertCourier2")
    public ResponseEntity<Map<String, Object>> insertReturnCourier(
            @RequestParam("courier") String courier,
            @RequestParam("codes") List<Integer> codes
    ) {
        int updatedCount = serviceOrderService.insertReturnCourierAndInvoice(codes, courier);

        Map<String, Object> result = new HashMap<>();
        result.put("isSuccess", updatedCount > 0);
        result.put("count", updatedCount);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/AdminOrderUpdate")
    public Map<String, Object> updateOrderState(
            @RequestParam("codes") List<Integer> codes,
            @RequestParam("state") String state
    ) {
        Map<String, Object> result = new HashMap<>();

        try {
            if (state == null || state.trim().isEmpty()) {
                result.put("isSuccess", false);
                result.put("message", "상태값이 유효하지 않습니다.");
                return result;
            }

            int updatedCount = serviceOrderService.updateOrderStates(codes, state);
            result.put("isSuccess", true);
            result.put("count", updatedCount);
        } catch (Exception e) {
            result.put("isSuccess", false);
            result.put("message", "에러 발생: " + e.getMessage());
        }

        return result;
    }

}
