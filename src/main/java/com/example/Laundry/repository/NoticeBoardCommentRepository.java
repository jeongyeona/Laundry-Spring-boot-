// Repository: com.example.Laundry.repository.NoticeBoardCommentRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.NoticeBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeBoardCommentRepository extends JpaRepository<NoticeBoardComment, Integer> {
}