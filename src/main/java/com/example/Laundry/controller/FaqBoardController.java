// Controller: com.example.Laundry.controller.FaqBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.FaqBoardCreateDto;
import com.example.Laundry.dto.FaqBoardResponseDto;
import com.example.Laundry.service.FaqBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
public class FaqBoardController {
    private final FaqBoardService service;

    public FaqBoardController(FaqBoardService service) {
        this.service = service;
    }

    @GetMapping
    public List<FaqBoardResponseDto> listAll() {
        return service.listAll();
    }

    @GetMapping("/{num}")
    public FaqBoardResponseDto getOne(@PathVariable Integer num) {
        return service.findById(num);
    }

    @PostMapping
    public ResponseEntity<FaqBoardResponseDto> create(@RequestBody FaqBoardCreateDto dto) {
        FaqBoardResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{num}")
    public FaqBoardResponseDto update(
            @PathVariable Integer num,
            @RequestBody FaqBoardCreateDto dto
    ) {
        return service.update(num, dto);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<Void> delete(@PathVariable Integer num) {
        service.delete(num);
        return ResponseEntity.noContent().build();
    }
}
