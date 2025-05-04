// Controller: com.example.Laundry.controller.OrderItemController.java
package com.example.Laundry.controller;

import com.example.Laundry.dto.OrderItemCreateDto;
import com.example.Laundry.dto.OrderItemResponseDto;
import com.example.Laundry.service.OrderItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    private final OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderItemResponseDto> listAll() {
        return service.listAll();
    }

    @GetMapping("/{num}")
    public OrderItemResponseDto getOne(@PathVariable Integer num) {
        return service.findById(num);
    }

    @PostMapping
    public ResponseEntity<OrderItemResponseDto> create(@RequestBody OrderItemCreateDto dto) {
        OrderItemResponseDto created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{num}")
    public OrderItemResponseDto update(
            @PathVariable Integer num,
            @RequestBody OrderItemCreateDto dto
    ) {
        return service.update(num, dto);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<Void> delete(@PathVariable Integer num) {
        service.delete(num);
        return ResponseEntity.noContent().build();
    }
}
