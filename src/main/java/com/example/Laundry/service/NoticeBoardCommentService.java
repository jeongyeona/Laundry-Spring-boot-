// Service: com.example.Laundry.service.NoticeBoardCommentService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.NoticeBoardComment;
import com.example.Laundry.dto.NoticeBoardCommentCreateDto;
import com.example.Laundry.dto.NoticeBoardCommentResponseDto;
import com.example.Laundry.mapper.NoticeBoardCommentMapper;
import com.example.Laundry.repository.NoticeBoardCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class NoticeBoardCommentService {
    private final NoticeBoardCommentRepository repo;
    private final NoticeBoardCommentMapper mapper = NoticeBoardCommentMapper.INSTANCE;

    public NoticeBoardCommentService(NoticeBoardCommentRepository repo) {
        this.repo = repo;
    }

    public List<NoticeBoardCommentResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public NoticeBoardCommentResponseDto create(NoticeBoardCommentCreateDto dto) {
        NoticeBoardComment entity = mapper.toEntity(dto);
        entity.setRegdate(dto.regdate());
        NoticeBoardComment saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public NoticeBoardCommentResponseDto findById(Integer num) {
        NoticeBoardComment entity = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found: " + num));
        return mapper.toDto(entity);
    }

    public NoticeBoardCommentResponseDto update(Integer num, NoticeBoardCommentCreateDto dto) {
        NoticeBoardComment existing = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found: " + num));
        existing.setWriter(dto.writer());
        existing.setContent(dto.content());
        existing.setTargetId(dto.targetId());
        existing.setRefGroup(dto.refGroup());
        existing.setCommentGroup(dto.commentGroup());
        existing.setDeleted(dto.deleted());
        existing.setRegdate(LocalDate.now());
        NoticeBoardComment updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer num) {
        if (!repo.existsById(num)) {
            throw new IllegalArgumentException("Comment not found: " + num);
        }
        repo.deleteById(num);
    }
}