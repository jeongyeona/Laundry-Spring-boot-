// Repository: com.example.Laundry.repository.ReviewBoardRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.NoticeBoard;
import com.example.Laundry.domain.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Integer> , JpaSpecificationExecutor<ReviewBoard> {
}