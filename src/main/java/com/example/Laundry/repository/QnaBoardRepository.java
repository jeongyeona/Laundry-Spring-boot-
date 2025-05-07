package com.example.Laundry.repository;

import com.example.Laundry.domain.QnaBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QnaBoardRepository
        extends JpaRepository<QnaBoard, Integer>,
        JpaSpecificationExecutor<QnaBoard> {
    @Modifying
    @Query("UPDATE QnaBoard q SET q.checkReply = 1 WHERE q.num = :num")
    int updateCheckReply(@Param("num") int num);

    @Modifying
    @Query("UPDATE QnaBoard q SET q.checkReply = 0 WHERE q.num = :num")
    int updateUncheckReply(@Param("num") int num);
}
