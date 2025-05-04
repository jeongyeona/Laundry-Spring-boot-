// Repository: com.example.Laundry.repository.ReviewBoardRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Integer> {
}