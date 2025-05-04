package com.example.Laundry.controller;

import com.example.Laundry.dto.CategorizeCreateDto;
import com.example.Laundry.dto.CategorizeResponseDto;
import com.example.Laundry.service.CategorizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorizeController {

    private final CategorizeService service;

    public CategorizeController(CategorizeService service) {
        this.service = service;
    }

    // 전체 조회
    @GetMapping
    public List<CategorizeResponseDto> listAll() {
        return service.listAll();
    }

    // 단건 조회
    @GetMapping("/{category}")
    public CategorizeResponseDto getOne(@PathVariable String category) {
        return service.findByCategory(category);
    }

    // 생성
    @PostMapping
    public ResponseEntity<CategorizeResponseDto> create(
            @RequestBody CategorizeCreateDto dto
    ) {
        CategorizeResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    // 삭제
    @DeleteMapping("/{category}")
    public ResponseEntity<Void> delete(@PathVariable String category) {
        service.delete(category);
        return ResponseEntity.noContent().build();
    }
}
