package com.example.Laundry.repository;

import com.example.Laundry.domain.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 공지사항 CRUD 및 검색 기능 제공 레포지토리
 */
public interface NoticeRepository extends JpaRepository<NoticeBoard, Integer>, JpaSpecificationExecutor<NoticeBoard> {
}
