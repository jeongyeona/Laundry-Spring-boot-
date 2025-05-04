// Service: com.example.Laundry.service.NoticeBoardService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.NoticeBoard;
import com.example.Laundry.dto.NoticeBoardCreateDto;
import com.example.Laundry.dto.NoticeBoardResponseDto;
import com.example.Laundry.mapper.NoticeBoardMapper;
import com.example.Laundry.repository.NoticeBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class NoticeBoardService {
    private final NoticeBoardRepository repo;
    private final NoticeBoardMapper mapper = NoticeBoardMapper.INSTANCE;

    public NoticeBoardService(NoticeBoardRepository repo) {
        this.repo = repo;
    }

    public List<NoticeBoardResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public NoticeBoardResponseDto create(NoticeBoardCreateDto dto) {
        NoticeBoard entity = mapper.toEntity(dto);
        entity.setRegdate(dto.regdate());
        NoticeBoard saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public NoticeBoardResponseDto findById(Integer num) {
        NoticeBoard entity = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + num));
        return mapper.toDto(entity);
    }

    public NoticeBoardResponseDto update(Integer num, NoticeBoardCreateDto dto) {
        NoticeBoard existing = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + num));
        existing.setWriter(dto.writer());
        existing.setTitle(dto.title());
        existing.setContent(dto.content());
        existing.setViewCount(dto.viewCount());
        existing.setRegdate(LocalDate.now());
        NoticeBoard updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer num) {
        if (!repo.existsById(num)) {
            throw new IllegalArgumentException("Notice not found: " + num);
        }
        repo.deleteById(num);
    }
}
