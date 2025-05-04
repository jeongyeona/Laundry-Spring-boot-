// Service: com.example.Laundry.service.ReplyBoardService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.ReplyBoard;
import com.example.Laundry.dto.ReplyBoardCreateDto;
import com.example.Laundry.dto.ReplyBoardResponseDto;
import com.example.Laundry.mapper.ReplyBoardMapper;
import com.example.Laundry.repository.ReplyBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReplyBoardService {
    private final ReplyBoardRepository repo;
    private final ReplyBoardMapper mapper = ReplyBoardMapper.INSTANCE;

    public ReplyBoardService(ReplyBoardRepository repo) {
        this.repo = repo;
    }

    public List<ReplyBoardResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ReplyBoardResponseDto create(ReplyBoardCreateDto dto) {
        ReplyBoard entity = mapper.toEntity(dto);
        entity.setRegdate(dto.regdate());
        ReplyBoard saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public ReplyBoardResponseDto findById(Integer rnum) {
        ReplyBoard entity = repo.findById(rnum)
                .orElseThrow(() -> new IllegalArgumentException("Reply not found: " + rnum));
        return mapper.toDto(entity);
    }

    public ReplyBoardResponseDto update(Integer rnum, ReplyBoardCreateDto dto) {
        ReplyBoard existing = repo.findById(rnum)
                .orElseThrow(() -> new IllegalArgumentException("Reply not found: " + rnum));
        existing.setRefNum(dto.refNum());
        existing.setWriter(dto.writer());
        existing.setContent(dto.content());
        existing.setUpdateDate(LocalDate.now());
        ReplyBoard updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer rnum) {
        if (!repo.existsById(rnum)) {
            throw new IllegalArgumentException("Reply not found: " + rnum);
        }
        repo.deleteById(rnum);
    }
}