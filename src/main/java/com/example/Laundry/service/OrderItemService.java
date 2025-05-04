// Service: com.example.Laundry.service.OrderItemService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.OrderItem;
import com.example.Laundry.dto.OrderItemCreateDto;
import com.example.Laundry.dto.OrderItemResponseDto;
import com.example.Laundry.mapper.OrderItemMapper;
import com.example.Laundry.repository.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderItemService {
    private final OrderItemRepository repo;
    private final OrderItemMapper mapper = OrderItemMapper.INSTANCE;

    public OrderItemService(OrderItemRepository repo) {
        this.repo = repo;
    }

    public List<OrderItemResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public OrderItemResponseDto create(OrderItemCreateDto dto) {
        OrderItem entity = mapper.toEntity(dto);
        OrderItem saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public OrderItemResponseDto findById(Integer num) {
        OrderItem entity = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found: " + num));
        return mapper.toDto(entity);
    }

    public OrderItemResponseDto update(Integer num, OrderItemCreateDto dto) {
        OrderItem existing = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found: " + num));
        existing.setCode(dto.code());
        existing.setInum(dto.inum());
        existing.setCount(dto.count());
        OrderItem updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer num) {
        if (!repo.existsById(num)) {
            throw new IllegalArgumentException("OrderItem not found: " + num);
        }
        repo.deleteById(num);
    }
}