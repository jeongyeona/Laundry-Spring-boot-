// Controller: com.example.Laundry.controller.ReviewBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.ReviewBoardCreateDto;
import com.example.Laundry.dto.ReviewBoardResponseDto;
import com.example.Laundry.service.ReviewBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewBoardController {
    private final ReviewBoardService service;

    public ReviewBoardController(ReviewBoardService service) {
        this.service = service;
    }

    @GetMapping
    public List<ReviewBoardResponseDto> listAll() {
        return service.listAll();
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