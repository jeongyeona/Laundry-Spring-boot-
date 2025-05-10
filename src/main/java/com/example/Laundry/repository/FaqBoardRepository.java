// Repository: com.example.Laundry.repository.FaqBoardRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.FaqBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FaqBoardRepository extends JpaRepository<FaqBoard, Integer>, JpaSpecificationExecutor<FaqBoard> {
    Page<FaqBoard> findByCategory(String category, Pageable pageable);
    Page<FaqBoard> findByCategoryAndTitleContaining(String category, String title, Pageable p);
    Page<FaqBoard> findByCategoryAndContentContaining(String category, String content, Pageable p);
}