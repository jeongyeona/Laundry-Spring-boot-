package com.example.Laundry.controller;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.example.Laundry.config.JwtTokenProvider;
import com.example.Laundry.config.JwtUtil;
import com.example.Laundry.domain.Items;
import com.example.Laundry.domain.OrderItem;
import com.example.Laundry.domain.ServiceOrder;
import com.example.Laundry.domain.User;
import com.example.Laundry.dto.*;
import com.example.Laundry.repository.OrderItemRepository;
import com.example.Laundry.repository.UserRepository;
import com.example.Laundry.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
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
    private final PasswordEncoder passwordEncoder;
    private final ServiceOrderService serviceOrderService;
    private final OrderItemService orderItemService;
    private final ItemsService itemsService;
    private final OrderItemRepository orderItemRepository;

    public LoginController(CountryPhoneService countryPhoneService, UserService userService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, ServiceOrderService serviceOrderService, OrderItemService orderItemService, ItemsService itemsService, OrderItemRepository orderItemRepository) {
        this.countryPhoneService = countryPhoneService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.serviceOrderService = serviceOrderService;
        this.orderItemService = orderItemService;
        this.itemsService = itemsService;
        this.orderItemRepository = orderItemRepository;
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

    // 이메일 검증
    @GetMapping("/CheckEmail")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
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
        ra.addFlashAttribute("updateSuccess", "비밀번호가 성공적으로 변경되었습니다.");
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

    //개인정보 수정 반영 요청 처리
    @RequestMapping("/Mypage/MyInfo")
    public String userInfoPage(HttpSession session, HttpServletRequest request, Model model) {
        String userId = (String) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            return "redirect:/login"; // 로그인 안 되어 있으면 로그인 페이지로
        }

        UserResponseDto dto = userService.findById(userId);
        model.addAttribute("contextPath", request.getContextPath()); // 추가
        model.addAttribute("id", userId);

        model.addAttribute("dto", dto);
        return "LoginInfo/Mypage/MyInfo";
    }

    @RequestMapping("/Mypage/MyInfoUpdateForm")
    public String showUpdateForm(HttpSession session, Model model) {
        String userId = (String) session.getAttribute("LOGIN_USER");
        if (userId == null) return "redirect:/LoginInfo/Login";

        UserResponseDto dto = userService.findById(userId);
        model.addAttribute("id", userId);
        model.addAttribute("dto", dto);
        List<CountryPhoneResponseDto> countryCodes = countryPhoneService.listAll();
        model.addAttribute("countryCodes", countryCodes);

        return "LoginInfo/Mypage/MyInfoUpdateForm";
    }

    // 파일 저장 위치는 application.properties 또는 하드코딩 가능
    @Value("${upload.path:/upload}")
    private String uploadPath;

    @PostMapping("/Mypage/ajax_profile_upload")
    public ResponseEntity<Map<String, String>> uploadProfile(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam("userId") String userId) {

        Map<String, String> result = new HashMap<>();

        if (imageFile.isEmpty()) {
            result.put("error", "파일이 비어 있습니다.");
            return ResponseEntity.badRequest().body(result);
        }

        String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String savedName = UUID.randomUUID().toString() + "_" + originalFilename;

        try {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File saveFile = new File(uploadDir, savedName);
            imageFile.transferTo(saveFile);

            String imagePath = "/upload/" + savedName;

            userService.updateProfileImage(userId, imagePath);

            result.put("imagePath", "/upload/" + savedName);
            return ResponseEntity.ok(result);

        } catch (IOException e) {
            result.put("error", "파일 저장 실패: " + e.getMessage());
            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping("/Mypage/MyInfoUpdate")
    public String updateUser(@ModelAttribute UserResponseDto dto, HttpSession session, RedirectAttributes ra) {
        String userId = (String) session.getAttribute("LOGIN_USER");

        User user = userRepository.findById(userId).orElseThrow();
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setAddr(dto.addr());
        user.setProfile(dto.profile());
        user.setCountryCode(dto.countryCode());

        userRepository.save(user);
        ra.addFlashAttribute("message", "회원정보가 수정되었습니다.");
        return "redirect:/LoginInfo/Mypage/MyInfo";
    }

    @GetMapping("/Mypage/MyInfoUpdateFormPwd")
    public String showUpdatePasswordForm() {
        return "LoginInfo/Mypage/MyInfoUpdateFormPwd";
    }

    // 비밀번호 변경
    @PostMapping("/Mypage/MyInfoUpdatePwd")
    public String myinfoupdatePwd(
            HttpSession session,
            @RequestParam String pwd,
            @RequestParam String newPwd,
            @RequestParam String newPwd2,
            RedirectAttributes ra
    ) {
        String userId = (String) session.getAttribute("LOGIN_USER");

        // 1) 비밀번호 일치 여부 체크
        if (!newPwd.equals(newPwd2)) {
            ra.addFlashAttribute("message", "새 비밀번호가 일치하지 않습니다.");
            return "redirect:/LoginInfo/Mypage/MyInfoUpdateFormPwd";
        }

        UserResponseDto user = userService.findById(userId);
        if (user == null || !passwordEncoder.matches(pwd, user.pwd())) {
            ra.addFlashAttribute("message", "기존 비밀번호가 올바르지 않습니다.");
            return "redirect:/LoginInfo/Mypage/MyInfoUpdateFormPwd";
        }

        // 2) 비밀번호 변경 시도
        boolean ok = userService.updatePassword(userId, newPwd);

        // 3) 성공 시 로그인 폼으로 리다이렉트
        ra.addFlashAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
        return "redirect:/LoginInfo/Mypage/MyInfo";
    }

    @PostMapping("/Mypage/MyInfoDelete")
    public String deleteUser(HttpSession session, RedirectAttributes ra) {
        String userId = (String) session.getAttribute("LOGIN_USER");

        if (userId == null) {
            ra.addFlashAttribute("message", "로그인이 필요합니다.");
            return "redirect:/LoginInfo/Login";
        }

        userService.delete(userId);

        session.invalidate(); // 세션 무효화 (로그아웃 처리)
        ra.addFlashAttribute("deletemessage", "회원 탈퇴가 완료되었습니다.");
        return "redirect:/";
    }

//    @GetMapping("/Mypage/OrderList")
//    public String getOrderList(
//            HttpSession session,
//            @RequestParam(defaultValue = "1") int pageNum,
//            @RequestParam(defaultValue = "5") int pageSize,
//            @RequestParam(defaultValue = "") String condition,
//            @RequestParam(defaultValue = "") String keyword,
//            @RequestParam(defaultValue = "") String state,
//            Model model
//    ) {
//        String userId = (String) session.getAttribute("LOGIN_USER");
//        if (userId != null) {
//            UserResponseDto user = userService.findById(userId);
//
//            // ✅ manager가 Y면 관리자 페이지로 리다이렉트
//            if ("Y".equalsIgnoreCase(user.manager())) {
//                return "LoginInfo/Mypage/admin/OrderList";
//            }
//
//            model.addAttribute("manager", user.manager());
//        } else {
//            model.addAttribute("manager", "N");
//        }
//
//        if (pageSize < 1) {
//            List<ServiceOrder> orders = serviceOrderService
//                    .getPagedOrders(userId, keyword, state, pageNum, pageSize)
//                    .getContent();
//            model.addAttribute("list", orders);
//        } else {
//            Page<ServiceOrder> page = serviceOrderService.getPagedOrders(userId, keyword, state, pageNum, pageSize);
//
//            int totalPages = page.getTotalPages();
//            int startPageNum = Math.max(1, pageNum - 2);
//            int endPageNum = Math.min(totalPages, pageNum + 2);
//
//            List<Integer> pageNumbers = IntStream
//                    .rangeClosed(startPageNum, endPageNum)
//                    .boxed()
//                    .toList();
//
//            model.addAttribute("list", page.getContent());
//            model.addAttribute("pageNum", pageNum);
//            model.addAttribute("startPageNum", startPageNum);
//            model.addAttribute("endPageNum", endPageNum);
//            model.addAttribute("totalPageCount", totalPages);
//            model.addAttribute("pageNumbers", pageNumbers);
//            model.addAttribute("id", userId);
//        }
//
//        model.addAttribute("condition", condition);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("state", state);
//
//        return "LoginInfo/Mypage/OrderList";
//    }

    @GetMapping("/Mypage/OrderList")
    public String getOrderList(
            HttpSession session,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "") String condition,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String state,
            Model model
    ) {
        pageNum = Math.max(pageNum, 1);

        String userId = (String) session.getAttribute("LOGIN_USER");

        if (userId != null) {
            UserResponseDto user = userService.findById(userId);
            boolean isManager = "Y".equalsIgnoreCase(user.manager());

            model.addAttribute("manager", user.manager());

            return getOrderListView(
                    isManager ? null : userId,
                    pageNum, pageSize, condition, keyword, state,
                    isManager ? "LoginInfo/Mypage/admin/OrderList" : "LoginInfo/Mypage/OrderList",
                    model
            );
        }

        model.addAttribute("manager", "N");

        return getOrderListView(
                null, pageNum, pageSize, condition, keyword, state,
                "LoginInfo/Mypage/OrderList", model
        );
    }

    private String getOrderListView(
            String userId,
            int pageNum, int pageSize,
            String condition, String keyword, String state,
            String viewName,
            Model model
    ) {
        Page<ServiceOrder> page = serviceOrderService.getPagedOrders(userId, keyword, state, pageNum, pageSize);

        int totalPages = page.getTotalPages();
        int startPageNum = Math.max(1, pageNum - 2);
        int endPageNum = Math.min(totalPages, pageNum + 2);

        List<Integer> pageNumbers = IntStream.rangeClosed(startPageNum, endPageNum).boxed().toList();

        model.addAttribute("list", page.getContent());
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("startPageNum", startPageNum);
        model.addAttribute("endPageNum", endPageNum);
        model.addAttribute("totalPageCount", totalPages);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("id", userId);
        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);
        model.addAttribute("state", state);

        return viewName;
    }



    @GetMapping("/Mypage/OrderDetail")
    public String getOrderDetail(
            HttpSession session,
            @RequestParam("code") Integer orderCode,
            Model model
    ) {
        String userId = (String) session.getAttribute("LOGIN_USER");
        if (userId == null) {
            return "redirect:/login"; // 로그인 안 되어 있으면 로그인 페이지로 이동
        }

        // 사용자 정보
        UserResponseDto user = userService.findById(userId);
        model.addAttribute("manager", user.manager());
        model.addAttribute("id", userId);

        // 주문 정보
        ServiceOrder order = serviceOrderService.findOrderByCode(orderCode);
        if (order == null || !order.getOrderer().equals(userId)) {
            return "redirect:/Mypage/OrderList"; // 해당 주문이 없거나 본인 주문이 아닌 경우 목록으로 리다이렉트
        }

        // 주문 품목
        List<OrderItem> orderItems = orderItemRepository.findWithItemsByOrderCode(orderCode);

        // inum 목록 추출 (String)
        List<Integer> inumList = orderItems.stream()
                .map(OrderItem::getInum)
                .collect(Collectors.toList());

        // DTO로 변환
        List<OrderItemResponseDto> itemDtos = orderItems.stream().map(item -> {
            Items product = item.getItem();
            return new OrderItemResponseDto(
                    item.getNum(),             // 주문 항목 번호
                    item.getCode(),            // 주문 코드
                    product.getInum(),         // 상품 번호
                    item.getCount(),           // 수량
                    product.getItemName(),     // 상품 이름
                    product.getPrice()         // 가격
            );
        }).toList();

        model.addAttribute("order", order);
        model.addAttribute("user", user);
        model.addAttribute("orderItems", itemDtos);

        return "LoginInfo/Mypage/OrderDetail";
    }

}
