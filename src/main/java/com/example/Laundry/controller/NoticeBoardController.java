// Controller: com.example.Laundry.controller.NoticeBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.NoticeBoardCreateDto;
import com.example.Laundry.dto.NoticeBoardResponseDto;
import com.example.Laundry.service.NoticeBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeBoardController {
    private final NoticeBoardService service;

    public NoticeBoardController(NoticeBoardService service) {
        this.service = service;
    }

    @GetMapping
    public List<NoticeBoardResponseDto> listAll() {
        return service.listAll();
    }

    @GetMapping("/{num}")
    public NoticeBoardResponseDto getOne(@PathVariable Integer num) {
        return service.findById(num);
    }

    @PostMapping
    public ResponseEntity<NoticeBoardResponseDto> create(@RequestBody NoticeBoardCreateDto dto) {
        NoticeBoardResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{num}")
    public NoticeBoardResponseDto update(
            @PathVariable Integer num,
            @RequestBody NoticeBoardCreateDto dto
    ) {
        return service.update(num, dto);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<Void> delete(@PathVariable Integer num) {
        service.delete(num);
        return ResponseEntity.noContent().build();
    }
}
