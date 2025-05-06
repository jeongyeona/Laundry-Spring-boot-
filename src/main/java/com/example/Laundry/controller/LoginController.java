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

    //로그인
    @PostMapping("/LoginPost")
    public String processLogin(
            @RequestParam String id,
            @RequestParam String pwd,
            HttpServletRequest request,
            Model model
    ) {
        try {
            boolean authenticated = userService.authenticate(id, pwd);

            if (authenticated) {
                // 1) 기존 세션 무효화
                request.getSession().invalidate();

                // 2) 새 세션 생성 후 로그인 사용자 속성만 세팅
                HttpSession newSession = request.getSession(true);
                newSession.setAttribute("LOGIN_USER", id);

                return "redirect:/";
            } else {
                // 비밀번호 불일치 등
                model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
                return "LoginInfo/Login";
            }

        } catch (UsernameNotFoundException ex) {
            // 아이디가 없는 경우
            model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "LoginInfo/Login";
        }
    }

    // 3) 로그아웃
    @GetMapping("/Logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 1) 세션 무효화
        request.getSession().invalidate();
        // 2) SecurityContext 초기화
        SecurityContextHolder.clearContext();
        // 3) 쿠키(JSESSIONID) 삭제(Optional)
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            // 세션 무효화 + SecurityContext 초기화 + 쿠키 삭제
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
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
    public String signUp(UserCreateDto dto) {
        userService.register(dto);
        // 가입 완료 후 로그인 페이지로 리다이렉트
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
