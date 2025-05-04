// Service: com.example.Laundry.service.ReviewBoardService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.ReviewBoard;
import com.example.Laundry.dto.ReviewBoardCreateDto;
import com.example.Laundry.dto.ReviewBoardResponseDto;
import com.example.Laundry.mapper.ReviewBoardMapper;
import com.example.Laundry.repository.ReviewBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReviewBoardService {
    private final ReviewBoardRepository repo;
    private final ReviewBoardMapper mapper = ReviewBoardMapper.INSTANCE;

    public ReviewBoardService(ReviewBoardRepository repo) {
        this.repo = repo;
    }

    public List<ReviewBoardResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ReviewBoardResponseDto create(ReviewBoardCreateDto dto) {
        ReviewBoard entity = mapper.toEntity(dto);
        entity.setRegdate(dto.regdate());
        ReviewBoard saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public ReviewBoardResponseDto findById(Integer num) {
        ReviewBoard entity = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + num));
        return mapper.toDto(entity);
    }

    public ReviewBoardResponseDto update(Integer num, ReviewBoardCreateDto dto) {
        ReviewBoard existing = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("Review not found: " + num));
        existing.setWriter(dto.writer());
        existing.setTitle(dto.title());
        existing.setContent(dto.content());
        existing.setViewCount(dto.viewCount());
        existing.setRegdate(LocalDate.now());
        existing.setStar(dto.star());
        ReviewBoard updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer num) {
        if (!repo.existsById(num)) {
            throw new IllegalArgumentException("Review not found: " + num);
        }
        repo.deleteById(num);
    }
}