package com.example.Laundry.controller;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.Laundry.config.JwtUtil;
import com.example.Laundry.dto.CountryPhoneResponseDto;
import com.example.Laundry.dto.UserCreateDto;
import com.example.Laundry.dto.UserResponseDto;
import com.example.Laundry.service.CountryPhoneService;
import com.example.Laundry.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.example.Laundry.dto.CountryPhoneCreateDto;
import com.example.Laundry.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/LoginInfo")
public class LoginController {

    private final CountryPhoneService countryPhoneService;
    private final UserService userService;

    public LoginController(CountryPhoneService countryPhoneService, UserService userService) {
        this.countryPhoneService = countryPhoneService;
        this.userService = userService;
    }

    //로그인 화면으로 이동
    @GetMapping("/Login")
    public String loginForm() {
        return "LoginInfo/Login";
    }

    //회원가입 화면으로 이동
    @GetMapping("/Signup")
    public String signupForm(Model model) {
        List<CountryPhoneResponseDto> countryCodes = countryPhoneService.listAll();
        model.addAttribute("countryCodes", countryCodes);
        return "LoginInfo/Signup";
    }

    // 회원가입
    @PostMapping("/SignupPost")
    public String signUp(UserCreateDto dto, RedirectAttributes ra) {
        userService.register(dto);
        ra.addFlashAttribute("signupSuccess", true);
        return "redirect:/LoginInfo/Login";
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
