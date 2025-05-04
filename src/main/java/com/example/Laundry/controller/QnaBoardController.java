// Controller: com.example.Laundry.controller.QnaBoardController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.QnaBoardCreateDto;
import com.example.Laundry.dto.QnaBoardResponseDto;
import com.example.Laundry.service.QnaBoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qna")
public class QnaBoardController {
    private final QnaBoardService service;

    public QnaBoardController(QnaBoardService service) {
        this.service = service;
    }

    @GetMapping
    public List<QnaBoardResponseDto> listAll() {
        return service.listAll();
    }

    @GetMapping("/{num}")
    public QnaBoardResponseDto getOne(@PathVariable Integer num) {
        return service.findById(num);
    }

    @PostMapping
    public ResponseEntity<QnaBoardResponseDto> create(@RequestBody QnaBoardCreateDto dto) {
        QnaBoardResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{num}")
    public QnaBoardResponseDto update(
            @PathVariable Integer num,
            @RequestBody QnaBoardCreateDto dto
    ) {
        return service.update(num, dto);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<Void> delete(@PathVariable Integer num) {
        service.delete(num);
        return ResponseEntity.noContent().build();
    }
}
