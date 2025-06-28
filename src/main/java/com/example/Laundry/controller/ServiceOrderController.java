// Controller: com.example.Laundry.controller.ServiceOrderController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.ItemsResponseDto;
import com.example.Laundry.dto.ServiceOrderCreateDto;
import com.example.Laundry.dto.ServiceOrderResponseDto;
import com.example.Laundry.dto.UserResponseDto;
import com.example.Laundry.service.ItemsService;
import com.example.Laundry.service.ServiceOrderService;
import com.example.Laundry.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/Reserve")
public class ServiceOrderController {
    private final ServiceOrderService service;
    private final UserService userService;
    private final ItemsService itemService;

    public ServiceOrderController(ServiceOrderService service, ItemsService itemService, UserService userService) {
        this.service = service;
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/ReserveSelect")
    public String selectList() {
        return "Reserve/ReserveSelect";
    }

    @GetMapping("/Select")
    public String select(
            @RequestParam("category") String category,
            Model model
    ) {
        model.addAttribute("category", category);
        List<ItemsResponseDto> items = itemService.getItemsByCategory(category);
        model.addAttribute("list", items);

        return "Reserve/Reserve";
    }

    /**
     * 결제 화면
     */
    @GetMapping("/Payment")
    public String showPaymentPage(
            @RequestParam String name,
            @RequestParam String price,
            @RequestParam String count,
            @RequestParam String date,
            @RequestParam String number,
            @RequestParam String category,
            @SessionAttribute(name = "LOGIN_USER", required = false) String userId,
            Model model
    ) {
        // 로그인된 유저의 기본 배송지 등을 DTO로 가져와서 모델에 담음
        UserResponseDto dto = null;
        if (userId != null) {
            dto = userService.findById(userId);
        }
        model.addAttribute("dto", dto);
        model.addAttribute("paramName", name);
        model.addAttribute("paramPrice", price);
        model.addAttribute("paramCount", count);
        model.addAttribute("paramDate", date);
        model.addAttribute("paramNumber", number);
        model.addAttribute("paramCategory", category);

        return "Reserve/Payment";
    }
}