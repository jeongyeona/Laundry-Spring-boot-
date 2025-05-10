// Controller: com.example.Laundry.controller.FaqBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.*;
import com.example.Laundry.service.FaqBoardService;
import com.example.Laundry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Faq")
public class FaqBoardController {
    private final FaqBoardService faqBoardService;
    private final UserService userService;

    public FaqBoardController(FaqBoardService faqBoardService, UserService userService) {
        this.faqBoardService = faqBoardService;
        this.userService = userService;
    }

    //로그인 화면으로 이동
    @GetMapping("/List")
    public String loginForm(
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

        if (pageSize < 1) {
            List<FaqBoardResponseDto> all = faqBoardService.listAll();
            model.addAttribute("list", all);
        } else {
            Page<FaqBoardResponseDto> page =
                    faqBoardService.findFaq(condition, keyword, pageNum, pageSize);

            int totalPages = page.getTotalPages();
            int startPageNum = Math.max(1, pageNum - 2);
            int endPageNum = Math.min(totalPages, pageNum + 2);

            List<Integer> pageNumbers = IntStream
                    .rangeClosed(startPageNum, endPageNum)
                    .boxed()
                    .toList();

            model.addAttribute("list", page.getContent());
            model.addAttribute("id", userId);
            model.addAttribute("pageNum", pageNum);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("startPageNum", startPageNum);
            model.addAttribute("endPageNum", endPageNum);
            model.addAttribute("totalPageCount", totalPages);
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);
        // 뷰 이름
        return "Faq/List";
    }

    @GetMapping("/Ajax")
    public String ajaxListByCategory(
            HttpSession session,
            @RequestParam String category,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "title_content") String condition,
            @RequestParam(defaultValue = "") String keyword,
            Model model
    ) {
        Page<FaqBoardResponseDto> page = faqBoardService.findByCategory(category, pageNum, pageSize, condition, keyword);
        int total = page.getTotalPages();
        int[] range = faqBoardService.calcPageRange(pageNum, total);
        String userId = (String) session.getAttribute("LOGIN_USER");

        List<Integer> pageNumbers =
                IntStream.rangeClosed(range[0], range[1])
                        .boxed()
                        .toList();

        model.addAttribute("list", page.getContent());
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("id", userId);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("startPageNum", range[0]);
        model.addAttribute("endPageNum", range[1]);
        model.addAttribute("totalPageCount", total);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);

        // fragment 조각만 반환
        return "fragments/faqTable :: faqRows";
    }

    @GetMapping("/FaqInsertForm")
    public String faqInsertForm() {
        return "Faq/FaqInsertForm";
    }

    @PostMapping("/FaqInsert")
    public String insert(
            HttpSession session,
            @RequestParam String category,
            @RequestParam String title,
            @RequestParam String content
    ) {
        String writer = (String) session.getAttribute("LOGIN_USER");
        FaqBoardCreateDto dto = new FaqBoardCreateDto(
                category,
                writer,
                title,
                content,
                LocalDateTime.now(),
                null
        );
        faqBoardService.create(dto);

        return "redirect:/Faq/List";
    }

    @GetMapping("/FaqUpdateForm")
    public String faqUpdateForm(
            HttpSession session,
            @ModelAttribute("dto") FaqBoardResponseDto dto,
            Model model
    ) {
        FaqBoardResponseDto full = faqBoardService.findById(dto.num());

        String userId = (String) session.getAttribute("LOGIN_USER");

        model.addAttribute("dto", full);
        model.addAttribute("id", userId);
        return "Faq/FaqUpdateForm";
    }

    @PostMapping("/FaqUpdate")
    public String updateNotice(
            HttpSession session,
            @ModelAttribute("dto") FaqBoardResponseDto dto,
            @SessionAttribute("LOGIN_USER") String loginUser,
            Model model,
            RedirectAttributes rttr
    ) {
        if (!dto.writer().equals(loginUser)) {
            // 권한 없으면 리스트로 돌려보내기
            return "redirect:/Faq/List";
        }

        // 2) DTO 조립 후 service 호출
        FaqBoardCreateDto createDto = new FaqBoardCreateDto(dto.category(), dto.writer(), dto.title(), dto.content(), dto.regdate(), dto.updateDate());
        faqBoardService.update(dto.num(), createDto);

        rttr.addFlashAttribute("updateSuccess", "수정되었습니다.");

        String userId = (String) session.getAttribute("LOGIN_USER");
        model.addAttribute("id", userId);

        // 1-3) 뷰에 바인딩
        model.addAttribute("dto", createDto);

        // 3) 리다이렉트 파라미터 (상세보기 num)
        rttr.addAttribute("num", dto.num());
        return "redirect:/Faq/List";
    }
}
