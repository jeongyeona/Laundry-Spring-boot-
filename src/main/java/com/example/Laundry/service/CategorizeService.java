package com.example.Laundry.service;

import com.example.Laundry.domain.Categorize;
import com.example.Laundry.dto.CategorizeCreateDto;
import com.example.Laundry.dto.CategorizeResponseDto;
import com.example.Laundry.mapper.CategorizeMapper;
import com.example.Laundry.repository.CategorizeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategorizeService {

    private final CategorizeRepository repo;
    private final CategorizeMapper mapper = CategorizeMapper.INSTANCE;

    public CategorizeService(CategorizeRepository repo) {
        this.repo = repo;
    }

    public List<CategorizeResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public CategorizeResponseDto create(CategorizeCreateDto dto) {
        Categorize entity = mapper.toEntity(dto);
        Categorize saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public CategorizeResponseDto findByCategory(String category) {
        Categorize entity = repo.findById(category)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + category));
        return mapper.toDto(entity);
    }

    public void delete(String category) {
        if (!repo.existsById(category)) {
            throw new IllegalArgumentException("Category not found: " + category);
        }
        repo.deleteById(category);
    }
}
