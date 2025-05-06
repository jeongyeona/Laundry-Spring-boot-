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
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ReplyBoardService {
    private final ReplyBoardRepository repo;
    private final ReplyBoardMapper mapper = ReplyBoardMapper.INSTANCE;

    public ReplyBoardService(ReplyBoardRepository repo) {
        this.repo = repo;
    }

    /** 특정 QnA 글(num)에 달린 댓글 조회 */
    public List<ReplyBoardResponseDto> listByRefNum(Integer refNum) {
        return repo.findAllByRefNum(refNum)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ReplyBoardResponseDto create(ReplyBoardCreateDto dto, String name) {
        ReplyBoard entity = mapper.toEntity(dto);
        entity.setRegdate(LocalDateTime.now());
        entity.setWriter(name);
        // 3) 저장
        ReplyBoard saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public ReplyBoardResponseDto update(Integer rnum, ReplyBoardCreateDto dto) {
        ReplyBoard existing = repo.findById(rnum)
                .orElseThrow(() -> new IllegalArgumentException("Reply not found: " + rnum));

        // dto 필드로 기존 엔티티 업데이트
        existing.setRefNum(dto.refNum());
        existing.setWriter(dto.writer());
        existing.setContent(dto.content());
        existing.setUpdateDate(LocalDate.now());

        // 저장 및 DTO 리턴
        ReplyBoard saved = repo.save(existing);
        return mapper.toDto(saved);
    }

    /** 댓글 삭제 */
    public void delete(Integer rnum) {
        if (!repo.existsById(rnum)) {
            throw new IllegalArgumentException("Reply not found: " + rnum);
        }
        repo.deleteById(rnum);
    }
}