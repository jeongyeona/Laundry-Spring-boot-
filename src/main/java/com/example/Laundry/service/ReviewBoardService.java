// Service: com.example.Laundry.service.ReviewBoardService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.ReviewBoard;
import com.example.Laundry.domain.ServiceOrder;
import com.example.Laundry.dto.ReviewBoardResponseDto;
import com.example.Laundry.mapper.ReviewBoardMapper;
import com.example.Laundry.repository.ReviewBoardRepository;
import com.example.Laundry.repository.ServiceOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ReviewBoardService {
    private final ReviewBoardRepository repo;
    private final ReviewBoardMapper mapper = ReviewBoardMapper.INSTANCE;
    private final ServiceOrderRepository serviceOrderRepository;

    public ReviewBoardService(ReviewBoardRepository repo, ServiceOrderRepository serviceOrderRepository) {
        this.repo = repo;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    public List<ReviewBoardResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // 페이징+검색 처리
    public Page<ReviewBoardResponseDto> findNotices(
            String condition,
            String keyword,
            int pageNum,
            int pageSize
    ) {
        // 0-based 페이지 인덱스
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("regdate").descending());

        Specification<ReviewBoard> spec = (root, query, cb) -> {
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

        Page<ReviewBoard> page = repo.findAll(spec, pageable);
        return page.map(mapper::toDto);
    }

    public void createReview(Integer code, String userId, String title, String content, Integer star) {

        // 1) 이 주문이 실제 존재하는지
        ServiceOrder order = serviceOrderRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        // 2) 로그인 유저가 주문자(owner)인지
        if (!order.getOrderer().equals(userId)) {
            throw new IllegalStateException("본인 주문만 후기 작성 가능합니다.");
        }

        // 3) 주문 상태가 반환완료인지
        if (!order.getState().equals("반환완료")) {
            throw new IllegalStateException("반환 완료된 주문만 후기 작성 가능합니다.");
        }

        // 4) 이미 리뷰가 존재하는지 (UNIQUE라서 DB단에서도 막히지만 서비스단에서도 체크)
        if (repo.existsByRefOrderCode(code)) {
            throw new IllegalStateException("이미 리뷰가 작성된 주문입니다.");
        }

        // 저장
        ReviewBoard review = new ReviewBoard();
        review.setWriter(userId);
        review.setTitle(title);
        review.setContent(content);
        review.setStar(star);
        review.setRefOrderCode(code);
        review.setRegdate(LocalDate.now());

        repo.save(review);
    }

    public ReviewBoardResponseDto findByRefOrderCode(Integer code) {
        return repo.findByRefOrderCode(code)
                .map(mapper::toDto)
                .orElse(null);
    }

    public Integer updateReview(Integer num, String userId,
                             String title, String content, Integer star) {

        ReviewBoard review = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("후기를 찾을 수 없습니다."));

        review.setTitle(title);
        review.setContent(content);
        review.setStar(star);

        return review.getRefOrderCode();
    }

    public void deleteReview(Integer num, String userId) {
        ReviewBoard review = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("후기를 찾을 수 없습니다."));


        repo.delete(review);
    }

    public ReviewBoardResponseDto findByRefOrderCodeWithViewCount(Integer code) {
        ReviewBoard review = repo.findByRefOrderCode(code)
                .orElse(null);

        if (review == null) {
            return null;
        }

        Integer current = review.getViewCount(); // 필드명에 맞게 수정
        if (current == null || current < 1) {
            review.setViewCount(1);
        } else {
            review.setViewCount(current + 1);
        }

        return new ReviewBoardResponseDto(
                review.getNum(),
                review.getWriter(),
                review.getTitle(),
                review.getContent(),
                review.getViewCount(),
                review.getRegdate(),
                review.getStar(),
                review.getRefOrderCode()
        );
    }
}