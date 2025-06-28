package com.example.Laundry.controller;

import com.example.Laundry.service.ServiceOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/Reserve")
public class ReserveViewController {

    private final ServiceOrderService service;

    public ReserveViewController(ServiceOrderService service) {
        this.service = service;
    }

    @PostMapping("/Refund")
    public String refundOrder(
            @RequestParam String merchantUid,
            @RequestParam String impUid,
            RedirectAttributes redirectAttributes) {

        System.out.println(">> Refund 요청 도착: " + merchantUid + ", " + impUid);

        boolean result = service.refundOrder(merchantUid, impUid); // 메서드 시그니처도 변경 필요
        if (result) {
            redirectAttributes.addFlashAttribute("refundMessage", "환불 요청이 완료되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("refundMessage", "환불 요청에 실패했습니다.");
        }
        return "redirect:/LoginInfo/Mypage/OrderList";
    }

    @PostMapping("/Insert")
    public String insertOrderWeb(
            @RequestParam String orderer,
            @RequestParam String product,
            @RequestParam String inum,
            @RequestParam String count,
            @RequestParam String reservation_date,
            @RequestParam String order_addr,
            @RequestParam String request,
            @RequestParam String username,
            @RequestParam String category,
            @RequestParam String productcount,
            @RequestParam BigDecimal order_price,
            @RequestParam(required = false) Long idx,
            @RequestParam String merchant_uid,
            @RequestParam String imp_uid,
            RedirectAttributes ra
    ) {
        service.saveOrder(orderer, product, inum, count,
                reservation_date, order_addr,
                request, username, category, productcount, order_price, merchant_uid, imp_uid);

        ra.addFlashAttribute("msg", "결제가 완료되었습니다.");
        return "redirect:/LoginInfo/Mypage/OrderList";
    }
}