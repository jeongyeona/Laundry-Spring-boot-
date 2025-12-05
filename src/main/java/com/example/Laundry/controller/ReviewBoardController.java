// Controller: com.example.Laundry.controller.ReviewBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.domain.OrderItem;
import com.example.Laundry.domain.ReviewBoard;
import com.example.Laundry.dto.*;
import com.example.Laundry.repository.OrderItemRepository;
import com.example.Laundry.service.OrderItemService;
import com.example.Laundry.service.ReviewBoardService;
import com.example.Laundry.service.ServiceOrderService;
import com.example.Laundry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Review")
public class ReviewBoardController {
    private final ReviewBoardService service;
    private final UserService userService;
    private final OrderItemService orderItemService;

    public ReviewBoardController(ReviewBoardService service, UserService userService, OrderItemService orderItemService) {
        this.service = service;
        this.userService = userService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/List")
    public String list(
            HttpSession session,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "title_content") String condition,
            @RequestParam(defaultValue = "") String keyword,
            Model model
    ) {
        String userId = (String) session.getAttribute("LOGIN_USER");
        if (userId != null) {
            UserResponseDto user = userService.findById(userId);
            model.addAttribute("manager", user.manager());  // e.g. "Y" or "N"
        } else {
            model.addAttribute("manager", "N");
        }

        pageNum  = pageNum  < 1 ? 1 : pageNum;
        pageSize = pageSize < 1 ? 5 : pageSize;

        if (pageSize < 1) {
            List<ReviewBoardResponseDto> all = service.listAll();
            model.addAttribute("list", all);
        } else {
            int pageIndex = pageNum - 1;

            Page<ReviewBoardResponseDto> page =
                    service.findNotices(condition, keyword, pageNum, pageSize);

            int totalPages = page.getTotalPages();
            int startPageNum = Math.max(1, pageNum - 2);
            int endPageNum = Math.min(totalPages, pageNum + 2);

            List<Integer> pageNumbers = IntStream
                    .rangeClosed(startPageNum, endPageNum)
                    .boxed()
                    .toList();

            model.addAttribute("list", page.getContent());
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("startPageNum", startPageNum);
            model.addAttribute("endPageNum", endPageNum);
            model.addAttribute("totalPageCount", totalPages);
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);
        // 뷰 이름
        return "Review/List";
    }

    @GetMapping("/ReviewDetail")
    public String insertForm(@RequestParam("code") Integer code,
                             HttpSession session,
                             Model model) {

        // 로그인 체크
        String userId = (String) session.getAttribute("LOGIN_USER");
        if (userId == null) {
            return "redirect:/Login";
        }

        UserResponseDto user = userService.findById(userId);

        model.addAttribute("loginUserId", userId);
        model.addAttribute("manager", user.manager());

        ReviewBoardResponseDto review = service.findByRefOrderCodeWithViewCount(code);
        model.addAttribute("review", review);

        List<OrderItem> orderItems = orderItemService.findOrderItems(code);
        String itemsText = orderItems.stream()
                .map(oi -> oi.getItem().getItem() + " " + oi.getCount() + "개")
                .collect(java.util.stream.Collectors.joining(", "));

        model.addAttribute("code", code);
        model.addAttribute("itemsText", itemsText);

        return "Review/ReviewDetail";
    }

    @PostMapping("/Insert")
    public String insert(@RequestParam("code") Integer code,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content,
                         @RequestParam("star") Integer star,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {

        String userId = (String) session.getAttribute("LOGIN_USER");
        if (userId == null) {
            return "redirect:/Login";
        }

        try {
            // 서비스 호출
            service.createReview(code, userId, title, content, star);

            // 성공 메시지 전달
            redirectAttributes.addFlashAttribute("msg", "후기가 저장되었습니다!");

            return "redirect:/Review/ReviewDetail?code=" + code;

        } catch (IllegalStateException | IllegalArgumentException e) {
            // 실패 메시지 전달
            redirectAttributes.addFlashAttribute("msg", e.getMessage());

            // 다시 작성 폼으로 보내기
            return "redirect:/Review/ReviewDetail?code=" + code;
        }
    }

    @PostMapping("/ReviewUpdate")
    public String reviewUpdate(@RequestParam("num") Integer num,
                               @RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("star") Integer star,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        String userId = (String) session.getAttribute("LOGIN_USER");
        if (userId == null) {
            return "redirect:/Login";
        }

        try {
            Integer code = service.updateReview(num, userId, title, content, star);
            redirectAttributes.addFlashAttribute("msg", "후기가 수정되었습니다.");
            return "redirect:/Review/ReviewDetail?code=" + code;

        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:/Review/ReviewUpdate?num=" + num;
        }
    }

    @PostMapping("/ReviewDelete")
    public String reviewDelete(@RequestParam("num") Integer num,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        String userId = (String) session.getAttribute("LOGIN_USER");
        if (userId == null) {
            return "redirect:/Login";
        }

        try {
            service.deleteReview(num, userId);
            redirectAttributes.addFlashAttribute("msg", "후기가 삭제되었습니다.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }

        return "redirect:/Review/List";
    }
}