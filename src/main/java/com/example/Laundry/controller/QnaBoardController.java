// Controller: com.example.Laundry.controller.QnaBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.NoticeBoardResponseDto;
import com.example.Laundry.dto.QnaBoardCreateDto;
import com.example.Laundry.dto.QnaBoardResponseDto;
import com.example.Laundry.dto.UserResponseDto;
import com.example.Laundry.service.QnaBoardService;
import com.example.Laundry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Qna")
public class QnaBoardController {
    private final QnaBoardService qnaBoardService;
    private final UserService userService;

    public QnaBoardController(QnaBoardService qnaBoardService, UserService userService) {
        this.qnaBoardService = qnaBoardService;
        this.userService = userService;
    }

    /**
     * pageSize ≥ 1 이면 페이징, 그렇지 않으면 전체 조회
     */
    @GetMapping("/List")
    public String list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "title_content") String condition,
            @RequestParam(defaultValue = "") String keyword,
            Principal principal,
            Model model
    ) {
        // 로그인 ID
        String loginUser = (principal != null ? principal.getName() : null);

        // 관리자 여부 조회 (UserResponseDto.manager() 가 "Y"/"N" 반환)
        String managerFlag = "N";
        if (loginUser != null) {
            UserResponseDto user = userService.findById(loginUser);
            managerFlag = user.manager();
        }

        model.addAttribute("id", loginUser);
        model.addAttribute("manager", managerFlag);
        model.addAttribute("condition", condition);
        model.addAttribute("keyword",   keyword);

        if (pageSize < 1) {
            // 전체조회
            List<QnaBoardResponseDto> all = qnaBoardService.listAll();
            model.addAttribute("list", all);
        } else {
            // 페이징 + 검색
            Page<QnaBoardResponseDto> page =
                    qnaBoardService.findQna(condition, keyword, pageNum, pageSize);

            int totalPages   = page.getTotalPages();
            int startPageNum = Math.max(1, pageNum - 2);
            int endPageNum   = Math.min(totalPages, pageNum + 2);

            List<Integer> pageNumbers = IntStream
                    .rangeClosed(startPageNum, endPageNum)
                    .boxed()
                    .toList();

            model.addAttribute("list",           page.getContent());
            model.addAttribute("pageNum",        pageNum);
            model.addAttribute("startPageNum",   startPageNum);
            model.addAttribute("endPageNum",     endPageNum);
            model.addAttribute("totalPageCount", totalPages);
            model.addAttribute("pageNumbers",    pageNumbers);
        }

        return "Qna/List";
    }

    /**
     * 상세 조회
     */
    @GetMapping("/QnaDetail")
    public String detail(
            @RequestParam("num") int num,
            Principal principal,
            Model model
    ) {
        String loginUser = principal != null ? principal.getName() : null;
        model.addAttribute("id", loginUser);

        String managerFlag = "N";
        if (loginUser != null) {
            UserResponseDto user = userService.findById(loginUser);
            managerFlag = user.manager();
        }
        model.addAttribute("manager", managerFlag);

        QnaBoardResponseDto dto = qnaBoardService.findById(num);
        //QnaReplyDto dtoReply = qnaBoardService.findReplyByRefNum(num);
        model.addAttribute("dto", dto);
        //model.addAttribute("dtoReply", dtoReply);

        return "Qna/QnaDetail";
    }

    /**
     * 1:1 문의 등록 폼
     */
    @GetMapping("/QnaInsertForm")
    public String showInsertForm(Model model, Principal principal) {
        // 로그인한 사용자 정보
        String loginUser = principal != null ? principal.getName() : null;
        model.addAttribute("id", loginUser);
        return "Qna/QnaInsertForm";
    }

    /**
     * 1:1 문의 등록 처리
     */
    @PostMapping("/QnaInsert")
    public String insert(
            QnaBoardCreateDto dto,
            Principal principal,
            RedirectAttributes rttr
    ) {
        // 작성자 설정
        String loginUser = principal != null ? principal.getName() : null;
        // 서비스 호출
        QnaBoardResponseDto saved = qnaBoardService.create(dto, loginUser);
        // 등록 후 상세화면으로 리다이렉트
        rttr.addAttribute("num", saved.num());
        return "redirect:/Qna/QnaDetail";
    }
}
