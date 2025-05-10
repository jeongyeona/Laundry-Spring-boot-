// Service: com.example.Laundry.service.FaqBoardService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.FaqBoard;
import com.example.Laundry.domain.NoticeBoard;
import com.example.Laundry.dto.FaqBoardCreateDto;
import com.example.Laundry.dto.FaqBoardResponseDto;
import com.example.Laundry.dto.NoticeBoardCreateDto;
import com.example.Laundry.dto.NoticeBoardResponseDto;
import com.example.Laundry.mapper.FaqBoardMapper;
import com.example.Laundry.repository.FaqBoardRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // 페이징+검색 처리
    public Page<FaqBoardResponseDto> findFaq(
            String condition,
            String keyword,
            int pageNum,
            int pageSize
    ) {
        // 0-based 페이지 인덱스
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("regdate").descending());

        Specification<FaqBoard> spec = (root, query, cb) -> {
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

        Page<FaqBoard> page = repo.findAll(spec, pageable);
        return page.map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<FaqBoardResponseDto> findByCategory(
            String category,
            int pageNum,
            int pageSize,
            String condition,
            String keyword
    ) {
        // 0-based, regdate 내림차순 정렬
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("regdate").descending());

        Specification<FaqBoard> spec = (root, query, cb) -> {
            // 1) 카테고리 필터
            Predicate pCategory = cb.equal(root.get("category"), category);

            // 2) 키워드가 없으면 '카테고리만' 필터
            if (keyword == null || keyword.isBlank()) {
                return pCategory;
            }

            // 3) 키워드 있으면 조건별 like
            String like = "%" + keyword.trim() + "%";
            Predicate pKeyword;
            switch (condition) {
                case "title":
                    pKeyword = cb.like(root.get("title"), like);
                    break;
                case "content":
                    pKeyword = cb.like(root.get("content"), like);
                    break;
                case "title_content":
                default:
                    pKeyword = cb.or(
                            cb.like(root.get("title"), like),
                            cb.like(root.get("content"), like)
                    );
                    break;
            }

            // 4) 카테고리 + 키워드 조합
            return cb.and(pCategory, pKeyword);
        };

        Page<FaqBoard> page = repo.findAll(spec, pageable);
        return page.map(mapper::toDto);
    }


    // 페이징 계산 유틸 메서드
    public int[] calcPageRange(int pageNum, int totalPages) {
        int start = Math.max(1, pageNum - 2);
        int end   = Math.min(totalPages, pageNum + 2);
        return new int[]{ start, end };
    }

    public FaqBoardResponseDto findById(Integer num) {
        FaqBoard entity = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("FAQ not found: " + num));
        return mapper.toDto(entity);
    }

    public FaqBoardResponseDto create(FaqBoardCreateDto dto) {
        FaqBoard entity = mapper.toEntity(dto);
        FaqBoard saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public FaqBoardResponseDto update(Integer num, FaqBoardCreateDto dto) {
        FaqBoard existing = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("FAQ not found: " + num));
        existing.setCategory(dto.category());
        existing.setWriter(dto.writer());
        existing.setTitle(dto.title());
        existing.setContent(dto.content());
        existing.setUpdateDate(LocalDateTime.now());
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