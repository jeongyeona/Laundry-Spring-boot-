// Repository: com.example.Laundry.repository.FaqBoardRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.FaqBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqBoardRepository extends JpaRepository<FaqBoard, Integer> {
}