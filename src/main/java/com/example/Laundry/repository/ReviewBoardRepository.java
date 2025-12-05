package com.example.Laundry.repository;

import com.example.Laundry.domain.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReviewBoardRepository
        extends JpaRepository<ReviewBoard, Integer>,
        JpaSpecificationExecutor<ReviewBoard> {

    // 주문번호로 이미 리뷰가 존재하는지 확인
    boolean existsByRefOrderCode(Integer refOrderCode);
    Optional<ReviewBoard> findByRefOrderCode(Integer refOrderCode);

}