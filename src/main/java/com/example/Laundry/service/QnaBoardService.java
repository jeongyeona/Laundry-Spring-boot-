// Service: com.example.Laundry.service.QnaBoardService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.NoticeBoard;
import com.example.Laundry.domain.QnaBoard;
import com.example.Laundry.dto.NoticeBoardResponseDto;
import com.example.Laundry.dto.QnaBoardCreateDto;
import com.example.Laundry.dto.QnaBoardResponseDto;
import com.example.Laundry.mapper.QnaBoardMapper;
import com.example.Laundry.repository.QnaBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class QnaBoardService {
    private final QnaBoardRepository repo;
    private final QnaBoardMapper mapper = QnaBoardMapper.INSTANCE;

    public QnaBoardService(QnaBoardRepository repo) {
        this.repo = repo;
    }

    public List<QnaBoardResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // 페이징+검색 처리
    public Page<QnaBoardResponseDto> findQna(
            String condition,
            String keyword,
            int pageNum,
            int pageSize
    ) {
        // 0-based 페이지 인덱스
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("regdate").descending());

        Specification<QnaBoard> spec = (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }
            String like = "%" + keyword.trim() + "%";
            switch (condition) {
                case "title":
                    return cb.like(root.get("title"), like);
                case "writer":
                    return cb.like(root.get("writer"), like);
                case "title_content":
                default:
                    return cb.or(
                            cb.like(root.get("title"), like),
                            cb.like(root.get("content"), like)
                    );
            }
        };

        Page<QnaBoard> page = repo.findAll(spec, pageable);
        return page.map(mapper::toDto);
    }

    public QnaBoardResponseDto create(QnaBoardCreateDto dto, String loginUser) {
        QnaBoard entity = mapper.toEntity(dto);
        entity.setRegdate(LocalDateTime.now());
        entity.setWriter(loginUser);
        entity.setCheckReply(0);
        entity.setFileSize(dto.fileSize() != null
                ? dto.fileSize()
                : 0);
        entity.setOrgFileName(dto.orgFileName() != null
                ? dto.orgFileName()
                : "");
        entity.setSaveFileName(dto.saveFileName() != null
                ? dto.saveFileName()
                : "");
        QnaBoard saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public QnaBoardResponseDto findById(Integer num) {
        QnaBoard entity = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("QnA not found: " + num));
        return mapper.toDto(entity);
    }

    public QnaBoardResponseDto update(Integer num, QnaBoardCreateDto dto) {
        QnaBoard existing = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("QnA not found: " + num));
        existing.setWriter(dto.writer());
        existing.setTitle(dto.title());
        existing.setContent(dto.content());
        existing.setRegdate(LocalDateTime.now());
        existing.setOrgFileName(dto.orgFileName());
        existing.setSaveFileName(dto.saveFileName());
        existing.setFileSize(dto.fileSize());
        existing.setCheckReply(dto.checkReply());
        QnaBoard updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer num) {
        if (!repo.existsById(num)) {
            throw new IllegalArgumentException("QnA not found: " + num);
        }
        repo.deleteById(num);
    }

    @Transactional
    public void replyUpdate(int num) {
        repo.updateCheckReply(num);
    }

    @Transactional
    public void unreplyUpdate(int num) {
        repo.updateUncheckReply(num);
    }
}
