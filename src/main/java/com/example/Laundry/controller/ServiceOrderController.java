// Controller: com.example.Laundry.controller.ServiceOrderController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.ItemsResponseDto;
import com.example.Laundry.dto.ServiceOrderCreateDto;
import com.example.Laundry.dto.ServiceOrderResponseDto;
import com.example.Laundry.service.ItemsService;
import com.example.Laundry.service.ServiceOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Reserve")
public class ServiceOrderController {
    private final ServiceOrderService service;
    private final ItemsService itemService;

    public ServiceOrderController(ServiceOrderService service, ItemsService itemService) {
        this.service = service;
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

        return "Reserve/Reserve" + category;
    }
}