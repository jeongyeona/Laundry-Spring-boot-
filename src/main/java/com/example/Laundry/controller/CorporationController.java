package com.example.Laundry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Corporation")
public class CorporationController {
    @GetMapping("/Brand")
    public String brand() {
        return "/Corporation/Brand";
    }

    @GetMapping("/History")
    public String history() {
        return "/Corporation/History";
    }

    @GetMapping("/Startup")
    public String startup() {
        return "/Corporation/Startup";
    }
}
