// Controller: com.example.Laundry.controller.ReviewBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.domain.ReviewBoard;
import com.example.Laundry.dto.NoticeBoardResponseDto;
import com.example.Laundry.dto.ReviewBoardCreateDto;
import com.example.Laundry.dto.ReviewBoardResponseDto;
import com.example.Laundry.dto.UserResponseDto;
import com.example.Laundry.service.ReviewBoardService;
import com.example.Laundry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Review")
public class ReviewBoardController {
    private final ReviewBoardService service;
    private final UserService userService;

    public ReviewBoardController(ReviewBoardService service, UserService userService) {
        this.service = service;
        this.userService = userService;
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

    @GetMapping("/{num}")
    public ReviewBoardResponseDto getOne(@PathVariable Integer num) {
        return service.findById(num);
    }

    @PostMapping
    public ResponseEntity<ReviewBoardResponseDto> create(@RequestBody ReviewBoardCreateDto dto) {
        ReviewBoardResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{num}")
    public ReviewBoardResponseDto update(
            @PathVariable Integer num,
            @RequestBody ReviewBoardCreateDto dto
    ) {
        return service.update(num, dto);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<Void> delete(@PathVariable Integer num) {
        service.delete(num);
        return ResponseEntity.noContent().build();
    }
}