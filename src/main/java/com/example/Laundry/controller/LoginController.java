package com.example.Laundry.controller;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.Laundry.dto.CountryPhoneResponseDto;
import com.example.Laundry.dto.UserCreateDto;
import com.example.Laundry.service.CountryPhoneService;
import com.example.Laundry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.Laundry.dto.CountryPhoneCreateDto;

@Controller
public class LoginController {

    private final CountryPhoneService countryPhoneService;
    private final UserService userService;

    public LoginController(CountryPhoneService countryPhoneService, UserService userService) {
        this.countryPhoneService = countryPhoneService;
        this.userService = userService;
    }

    // 국가 번호 목록 조회
    @ModelAttribute("countryCodes")
    public List<CountryPhoneResponseDto> countryCodes() {
        return countryPhoneService.listAll();
    }

    //로그인 화면으로 이동
    @GetMapping("/Login")
    public String loginForm() {
        return "LoginInfo/Login";
    }

    //로그인하기

    //로그아웃

    //회원가입 화면으로 이동
    @GetMapping("/Signup")
    public String signupForm() {
        return "LoginInfo/Signup";
    }

    //회원가입
    @PostMapping("/SignupPost")
    public String signUp(UserCreateDto dto) {
        userService.register(dto);
        return "mypage/login/signup";
    }

    //비밀번호 찾기 화면으로 이동
    @RequestMapping("/FindPwd")
    public String findPwdForm() {
        return "LoginInfo/FindPwd";
    }

    //아이디 찾기 화면으로 이동
    @RequestMapping("/FindId")
    public String findPwd() {
        return "LoginInfo/FindId";
    }

    //ajax를 이용해서 비밀번호 수정form 가져오기
    @RequestMapping("/users/update_pwd_form")
    public String ajaxUpdatePwdForm(String id){
        return "mypage/login/update_pwd_form";
    }
}
