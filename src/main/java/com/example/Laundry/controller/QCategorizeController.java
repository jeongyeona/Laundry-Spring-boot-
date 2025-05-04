// Controller: com.example.Laundry.controller.QCategorizeController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.QCategorizeCreateDto;
import com.example.Laundry.dto.QCategorizeResponseDto;
import com.example.Laundry.service.QCategorizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qcategories")
public class QCategorizeController {
    private final QCategorizeService service;

    public QCategorizeController(QCategorizeService service) {
        this.service = service;
    }

    @GetMapping
    public List<QCategorizeResponseDto> listAll() {
        return service.listAll();
    }

    @GetMapping("/{category}")
    public QCategorizeResponseDto getOne(@PathVariable String category) {
        return service.findByCategory(category);
    }

    @PostMapping
    public ResponseEntity<QCategorizeResponseDto> create(@RequestBody QCategorizeCreateDto dto) {
        QCategorizeResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/{category}")
    public ResponseEntity<Void> delete(@PathVariable String category) {
        service.delete(category);
        return ResponseEntity.noContent().build();
    }
}
