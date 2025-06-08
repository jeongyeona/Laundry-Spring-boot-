// Repository: com.example.Laundry.repository.OrderItemRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.OrderItem;
import com.example.Laundry.domain.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByCode(Integer orderCode);
}