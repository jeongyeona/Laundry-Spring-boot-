// Repository: com.example.Laundry.repository.ServiceOrderRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Integer> {
    List<ServiceOrder> findAllByCategory(String category);
}