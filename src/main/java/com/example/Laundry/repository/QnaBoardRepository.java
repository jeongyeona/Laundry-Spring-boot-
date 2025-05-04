// Repository: com.example.Laundry.repository.QnaBoardRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.QnaBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaBoardRepository extends JpaRepository<QnaBoard, Integer> {
}