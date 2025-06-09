// Repository: com.example.Laundry.repository.OrderItemRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.OrderItem;
import com.example.Laundry.domain.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByCode(Integer orderCode);
    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.item WHERE oi.code = :orderCode")
    List<OrderItem> findWithItemsByOrderCode(@Param("orderCode") Integer orderCode);
}