// Repository: com.example.Laundry.repository.ServiceOrderRepository.java
package com.example.Laundry.repository;

import com.example.Laundry.domain.ServiceOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Integer> {
    List<ServiceOrder> findAllByCategory(String category);
    Page<ServiceOrder> findByOrdererAndState(
            String orderer, String state, Pageable pageable
    );
    Page<ServiceOrder> findByOrderer(
            String orderer, Pageable pageable
    );
}