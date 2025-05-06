package com.example.Laundry.repository;

import com.example.Laundry.domain.QnaBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QnaBoardRepository
        extends JpaRepository<QnaBoard, Integer>,
        JpaSpecificationExecutor<QnaBoard> {
    // 이제 findAll(Specification, Pageable) 을 사용할 수 있습니다.
}
