package com.example.Laundry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")      // 루트 요청
	public String home() {
		return "home";      // templates/home.html
	}
}
