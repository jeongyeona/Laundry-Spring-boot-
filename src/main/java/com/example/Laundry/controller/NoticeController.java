package com.example.Laundry.controller;

import com.example.Laundry.dto.NoticeBoardCommentResponseDto;
import com.example.Laundry.dto.NoticeBoardCreateDto;
import com.example.Laundry.dto.NoticeBoardResponseDto;
import com.example.Laundry.dto.UserResponseDto;
import com.example.Laundry.service.NoticeService;
import com.example.Laundry.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;


    public NoticeController(NoticeService noticeService, UserService userService) {
        this.noticeService = noticeService;
        this.userService = userService;
    }

    /**
     * pageSize ≥ 1 이면 페이징, 그렇지 않으면 전체 조회
     */
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

        if (pageSize < 1) {
            List<NoticeBoardResponseDto> all = noticeService.listAll();
            model.addAttribute("list", all);
        } else {
            Page<NoticeBoardResponseDto> page =
                    noticeService.findNotices(condition, keyword, pageNum, pageSize);

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
        return "Notice/List";
    }

    @GetMapping("/NoticeInsertForm")
    public String noticeInsertForm() {
        return "Notice/NoticeInsertForm";
    }

    @PostMapping("/NoticeInsert")
    public String insert(
            HttpSession session,
            @RequestParam String title,
            @RequestParam String content
    ) {
        String writer = (String) session.getAttribute("LOGIN_USER");
        NoticeBoardCreateDto dto = new NoticeBoardCreateDto(
                writer,
                title,
                content,
                0,
                LocalDateTime.now()
        );
        noticeService.create(dto);

        return "redirect:/Notice/List";
    }

    // Detail
    @GetMapping("/NoticeDetail")
    public String detail(
            HttpSession session,
            @RequestParam("num") int num,
            @RequestParam(defaultValue = "title_content") String condition,
            @RequestParam(defaultValue = "") String keyword,
            Model model
    ) {
        String userId = (String) session.getAttribute("LOGIN_USER");
        model.addAttribute("id", userId);

        // 2) **조회수 1 증가**
        noticeService.incrementViewCount(num);

        NoticeBoardResponseDto dto = noticeService.findById(num);
        model.addAttribute("dto", dto);

        model.addAttribute("condition", condition);
        model.addAttribute("keyword", keyword);

        //List<NoticeBoardCommentResponseDto> commentList = noticeService.listByRefGroup(num);
        //model.addAttribute("commentList", commentList);

        // 5) 댓글 페이징을 쓴다면 총 페이지 수도
        //int totalPageCount = noticeService.totalPageCount(num);
        //model.addAttribute("totalPageCount", totalPageCount);

        return "Notice/NoticeDetail";  // templates/Notice/NoticeDetail.html
    }
}