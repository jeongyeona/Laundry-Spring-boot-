package com.example.Laundry.controller;

import com.example.Laundry.service.CategorizeService;
import com.example.Laundry.service.ItemsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Guide")
public class GuideController {

    private final CategorizeService categorizeService;
    private final ItemsService itemsService;

    public GuideController(CategorizeService categorizeService, ItemsService itemsService) {
        this.categorizeService = categorizeService;
        this.itemsService = itemsService;
    }

    @GetMapping("/PriceGuide")      // 루트 요청
    public String priceguide(Model model) {
        model.addAttribute("list",
                itemsService.findByCategory("clothes")
        );
        return "Guide/PriceGuide";
    }

    @GetMapping(value="/PriceGuideFragment", produces="text/html")
    public String priceGuideFragment(
            @RequestParam String category,
            Model model) {

        model.addAttribute("list",
                itemsService.findByCategory(category)
        );
        return "Guide/PriceGuide :: itemsTableDiv";
    }

    @GetMapping("/AreaGuide")      // 루트 요청
    public String areaGuide() {
        return "Guide/AreaGuide";
    }
}
