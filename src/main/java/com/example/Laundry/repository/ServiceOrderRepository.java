// Repository: com.example.Laundry.repository.ServiceOrderRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.OrderItem;
import com.example.Laundry.domain.ServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Integer> {
    List<ServiceOrder> findAllByCategory(String category);
    Page<ServiceOrder> findByOrdererAndState(
            String orderer, String state, Pageable pageable
    );
    Page<ServiceOrder> findByOrderer(
            String orderer, Pageable pageable
    );
    Optional<ServiceOrder> findByCode(Integer code);
    Optional<ServiceOrder> findByMerchantUid(String merchantUid);
    Page<ServiceOrder> findByState(String state, Pageable pageable);

    @Query("SELECT s FROM ServiceOrder s WHERE " +
            "((:condition = 'code' AND CONCAT('', s.code) LIKE CONCAT('%', :keyword, '%')) OR " +
            " (:condition = 'orderer_name' AND s.orderer LIKE CONCAT('%', :keyword, '%')))")
    Page<ServiceOrder> findByCondition(@Param("condition") String condition,
                                       @Param("keyword") String keyword,
                                       Pageable pageable);

    @Query("SELECT s FROM ServiceOrder s WHERE " +
            "((:condition = 'code' AND CONCAT('', s.code) LIKE CONCAT('%', :keyword, '%')) OR " +
            " (:condition = 'orderer_name' AND s.orderer LIKE CONCAT('%', :keyword, '%'))) AND " +
            "(:state IS NULL OR :state = '' OR s.state = :state)")
    Page<ServiceOrder> findByConditionAndState(@Param("condition") String condition,
                                               @Param("keyword") String keyword,
                                               @Param("state") String state,
                                               Pageable pageable);

}