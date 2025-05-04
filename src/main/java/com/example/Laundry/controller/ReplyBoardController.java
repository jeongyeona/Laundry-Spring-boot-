// Controller: com.example.Laundry.controller.ReplyBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.ReplyBoardCreateDto;
import com.example.Laundry.dto.ReplyBoardResponseDto;
import com.example.Laundry.service.ReplyBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/replies")
public class ReplyBoardController {
    private final ReplyBoardService service;

    public ReplyBoardController(ReplyBoardService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReplyBoardResponseDto> listAll() {
        return service.listAll();
    }

    @GetMapping("/{rnum}")
    public ReplyBoardResponseDto getOne(@PathVariable Integer rnum) {
        return service.findById(rnum);
    }

    @PostMapping
    public ResponseEntity<ReplyBoardResponseDto> create(@RequestBody ReplyBoardCreateDto dto) {
        ReplyBoardResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{rnum}")
    public ReplyBoardResponseDto update(
            @PathVariable Integer rnum,
            @RequestBody ReplyBoardCreateDto dto
    ) {
        return service.update(rnum, dto);
    }

    @DeleteMapping("/{rnum}")
    public ResponseEntity<Void> delete(@PathVariable Integer rnum) {
        service.delete(rnum);
        return ResponseEntity.noContent().build();
    }
}
