// Service: com.example.Laundry.service.QCategorizeService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.QCategorize;
import com.example.Laundry.dto.QCategorizeCreateDto;
import com.example.Laundry.dto.QCategorizeResponseDto;
import com.example.Laundry.mapper.QCategorizeMapper;
import com.example.Laundry.repository.QCategorizeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QCategorizeService {
    private final QCategorizeRepository repo;
    private final QCategorizeMapper mapper = QCategorizeMapper.INSTANCE;

    public QCategorizeService(QCategorizeRepository repo) {
        this.repo = repo;
    }

    public List<QCategorizeResponseDto> listAll() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public QCategorizeResponseDto create(QCategorizeCreateDto dto) {
        QCategorize entity = mapper.toEntity(dto);
        QCategorize saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public QCategorizeResponseDto findByCategory(String category) {
        QCategorize entity = repo.findById(category)
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