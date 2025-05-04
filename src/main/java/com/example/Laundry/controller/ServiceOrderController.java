// Controller: com.example.Laundry.controller.ServiceOrderController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.ServiceOrderCreateDto;
import com.example.Laundry.dto.ServiceOrderResponseDto;
import com.example.Laundry.service.ServiceOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service-orders")
public class ServiceOrderController {
    private final ServiceOrderService service;

    public ServiceOrderController(ServiceOrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<ServiceOrderResponseDto> listAll() {
        return service.listAll();
    }

    @GetMapping("/{code}")
    public ServiceOrderResponseDto getOne(@PathVariable Integer code) {
        return service.findById(code);
    }

    @PostMapping
    public ResponseEntity<ServiceOrderResponseDto> create(@RequestBody ServiceOrderCreateDto dto) {
        ServiceOrderResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{code}")
    public ServiceOrderResponseDto update(
            @PathVariable Integer code,
            @RequestBody ServiceOrderCreateDto dto
    ) {
        return service.update(code, dto);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable Integer code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }
}