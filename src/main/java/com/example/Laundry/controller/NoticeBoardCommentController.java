// Controller: com.example.Laundry.controller.NoticeBoardCommentController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.NoticeBoardCommentCreateDto;
import com.example.Laundry.dto.NoticeBoardCommentResponseDto;
import com.example.Laundry.service.NoticeBoardCommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice-comments")
public class NoticeBoardCommentController {
    private final NoticeBoardCommentService service;

    public NoticeBoardCommentController(NoticeBoardCommentService service) {
        this.service = service;
    }

    @GetMapping
    public List<NoticeBoardCommentResponseDto> listAll() {
        return service.listAll();
    }

    @GetMapping("/{num}")
    public NoticeBoardCommentResponseDto getOne(@PathVariable Integer num) {
        return service.findById(num);
    }

    @PostMapping
    public ResponseEntity<NoticeBoardCommentResponseDto> create(@RequestBody NoticeBoardCommentCreateDto dto) {
        NoticeBoardCommentResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{num}")
    public NoticeBoardCommentResponseDto update(
            @PathVariable Integer num,
            @RequestBody NoticeBoardCommentCreateDto dto
    ) {
        return service.update(num, dto);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<Void> delete(@PathVariable Integer num) {
        service.delete(num);
        return ResponseEntity.noContent().build();
    }
}
