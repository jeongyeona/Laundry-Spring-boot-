// Repository: com.example.Laundry.repository.NoticeBoardRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer> {
}