// src/main/java/com/example/Laundry/controller/UserApiController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.UserCreateDto;
import com.example.Laundry.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/LoginInfo")
public class RestLoginController {
    private final UserService userService;
    public RestLoginController(UserService userService) {
        this.userService = userService;
    }

    //아이디 중복체크
    @GetMapping("/CheckId")
    public boolean checkId(@RequestParam String inputId) {
        return userService.checkId(inputId);
    }
}
