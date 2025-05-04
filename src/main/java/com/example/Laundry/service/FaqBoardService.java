// Service: com.example.Laundry.service.FaqBoardService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.FaqBoard;
import com.example.Laundry.dto.FaqBoardCreateDto;
import com.example.Laundry.dto.FaqBoardResponseDto;
import com.example.Laundry.mapper.FaqBoardMapper;
import com.example.Laundry.repository.FaqBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class FaqBoardService {
    private final FaqBoardRepository repo;
    private final FaqBoardMapper mapper = FaqBoardMapper.INSTANCE;

    public FaqBoardService(FaqBoardRepository repo) {
        this.repo = repo;
    }

    public List<FaqBoardResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public FaqBoardResponseDto create(FaqBoardCreateDto dto) {
        FaqBoard entity = mapper.toEntity(dto);
        entity.setRegdate(dto.regdate());
        FaqBoard saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public FaqBoardResponseDto findById(Integer num) {
        FaqBoard entity = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("FAQ not found: " + num));
        return mapper.toDto(entity);
    }

    public FaqBoardResponseDto update(Integer num, FaqBoardCreateDto dto) {
        FaqBoard existing = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("FAQ not found: " + num));
        existing.setCategory(dto.category());
        existing.setWriter(dto.writer());
        existing.setTitle(dto.title());
        existing.setContent(dto.content());
        existing.setUpdateDate(LocalDate.now());
        FaqBoard updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer num) {
        if (!repo.existsById(num)) {
            throw new IllegalArgumentException("FAQ not found: " + num);
        }
        repo.deleteById(num);
    }
}