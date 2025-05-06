// Controller: com.example.Laundry.controller.QnaBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.*;
import com.example.Laundry.service.QnaBoardService;
import com.example.Laundry.service.ReplyBoardService;
import com.example.Laundry.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/Qna")
public class QnaBoardController {
    private final QnaBoardService qnaBoardService;
    private final UserService userService;
    private final ReplyBoardService replyBoardService;

    public QnaBoardController(QnaBoardService qnaBoardService, UserService userService, ReplyBoardService replyBoardService) {
        this.qnaBoardService = qnaBoardService;
        this.userService = userService;
        this.replyBoardService = replyBoardService;
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
            Model model
    ) {
        // 1) SecurityContext에서 Authentication 꺼내기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 2) 로그인 사용자 이름 구하기 (익명이면 null)
        String loginUser = null;
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            loginUser = auth.getName();
        }
        model.addAttribute("id", loginUser);

        // 3) 관리자 여부 플래그
        String managerFlag = "N";
        if (loginUser != null) {
            UserResponseDto user = userService.findById(loginUser);
            managerFlag = user.manager();
        }
        model.addAttribute("manager", managerFlag);

        // 4) QnA 본문과 댓글 리스트
        QnaBoardResponseDto dto = qnaBoardService.findById(num);
        List<ReplyBoardResponseDto> replies = replyBoardService.listByRefNum(num);
        model.addAttribute("dto", dto);
        model.addAttribute("dtoReplies", replies);

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

    /** 댓글 등록 */
    @PostMapping("/ReplyInsert")
    public String replyInsert(@ModelAttribute ReplyBoardCreateDto dto,
                              Principal principal) {
        // 작성자, 등록일 설정
        var name = principal.getName();
        replyBoardService.create(dto, name);
        return "redirect:/Qna/QnaDetail?num=" + dto.refNum();
    }

    /** 댓글 수정 (AJAX) */
    @PostMapping("/ReplyUpdate")
    @ResponseBody
    public boolean replyUpdate(@RequestParam("rnum") Integer rnum,
                               @RequestParam("refNum") Integer refNum,
                               @RequestParam("content") String content,
                               Principal principal
    ) {
        String writer = principal.getName();
        ReplyBoardCreateDto dto = new ReplyBoardCreateDto(refNum, writer, content, null);
        replyBoardService.update(rnum, dto);
        return true;
    }

    /** 댓글 삭제 (AJAX) */
    @GetMapping("/ReplyDelete")
    @ResponseBody
    public boolean replyDelete(@RequestParam("num") Integer rnum) {
        replyBoardService.delete(rnum);
        return true;
    }

    /** QnA 글 삭제 */
    @GetMapping("/QnaDelete")
    public String delete(@RequestParam("num") Integer num) {
        qnaBoardService.delete(num);
        return "redirect:/Qna/List";
    }
}
