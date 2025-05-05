package com.example.Laundry.repository;

import com.example.Laundry.domain.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Integer> {

    /**
     * 지정한 카테고리에 속하는 모든 Items 엔티티를 조회합니다.
     * — 엔티티의 'category' 필드와 정확히 매칭되어야 합니다.
     */
    List<Items> findAllByCategory(String category);

}
