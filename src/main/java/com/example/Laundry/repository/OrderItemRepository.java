// Repository: com.example.Laundry.repository.OrderItemRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}