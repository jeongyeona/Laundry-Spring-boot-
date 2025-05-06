// Repository: com.example.Laundry.repository.ReplyBoardRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.ReplyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyBoardRepository extends JpaRepository<ReplyBoard, Integer> {
    List<ReplyBoard> findAllByRefNum(Integer refNum);
}