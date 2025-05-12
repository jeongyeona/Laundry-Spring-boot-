package com.example.Laundry.controller;
import java.net.URLEncoder;
import java.util.*;

import com.example.Laundry.config.JwtTokenProvider;
import com.example.Laundry.config.JwtUtil;
import com.example.Laundry.dto.CountryPhoneResponseDto;
import com.example.Laundry.dto.UserCreateDto;
import com.example.Laundry.dto.UserResponseDto;
import com.example.Laundry.repository.UserRepository;
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
import org.springframework.security.core.GrantedAuthority;
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
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;



    public LoginController(CountryPhoneService countryPhoneService, UserService userService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.countryPhoneService = countryPhoneService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
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

    // 회원가입
    @PostMapping("/SignupPost")
    public String signUp(UserCreateDto dto, RedirectAttributes ra) {
        userService.register(dto);
        ra.addFlashAttribute("signupSuccess", true);
        return "redirect:/LoginInfo/Login";
    }

    @GetMapping("/CheckEmail")
    @ResponseBody
    public boolean checkEmail(@RequestParam("inputEmail") String email) {
        return userService.emailExists(email);
    }

    //비밀번호 찾기 화면으로 이동
    @RequestMapping("/FindPwd")
    public String findPwdForm() {
        return "LoginInfo/FindPwd";
    }

    @PostMapping("/FindPwdCheck")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkPwdUser(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String email
    ) {
        boolean valid = userService.existsByIdAndNameAndEmail(id, name, email);
        Map<String, Object> result = new HashMap<>();
        result.put("valid", valid);
        return ResponseEntity.ok(result);
    }

    //비밀번호 찾기 화면으로 이동
    @PostMapping("/FindPwdForm")
    public String ajaxUpdatePwdForm(
            @RequestParam("id") String id,
            Model model
    ) {
        model.addAttribute("id", id);
        return "fragments/FindPwdForm :: updatePwdForm";
    }

    // 비밀번호 변경
    @PostMapping("/UpdatePwd")
    public String updatePwd(
            @RequestParam String id,
            @RequestParam String pwd,
            @RequestParam String pwd2,
            RedirectAttributes ra
    ) {
        // 1) 비밀번호 일치 여부 체크
        if (!pwd.equals(pwd2)) {
            ra.addFlashAttribute("updateError", "비밀번호가 일치하지 않습니다.");
            return "redirect:/LoginInfo/FindPwd";
        }

        // 2) 서비스 호출
        boolean ok = userService.updatePassword(id, pwd);
        if (!ok) {
            ra.addFlashAttribute("updateError", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
            return "redirect:/LoginInfo/FindPwd";
        }

        // 3) 성공 시 로그인 폼으로 리다이렉트
        ra.addFlashAttribute("updateSuccess", "비밀번호가 성공적으로 변경되었습니다. 로그인해주세요.");
        return "redirect:/LoginInfo/Login";
    }


    //아이디 찾기 화면으로 이동
    @RequestMapping("/FindId")
    public String findPwd() {
        return "LoginInfo/FindId";
    }

    @PostMapping("/CheckUser")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkUser(
            @RequestParam String name,
            @RequestParam String email
    ) {
        UserResponseDto dto = userService.findByNameAndEmail(name, email);
        Map<String, Object> result = new HashMap<>();
        if (dto != null) {
            Map<String, String> u = new HashMap<>();
            u.put("id", dto.id());
            result.put("user", u);
        } else {
            result.put("user", null);
        }
        return ResponseEntity.ok(result);
    }
}
