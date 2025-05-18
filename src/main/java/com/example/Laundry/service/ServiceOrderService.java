// Service: com.example.Laundry.service.ServiceOrderService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.ServiceOrder;
import com.example.Laundry.dto.ServiceOrderCreateDto;
import com.example.Laundry.dto.ServiceOrderResponseDto;
import com.example.Laundry.mapper.ServiceOrderMapper;
import com.example.Laundry.repository.ServiceOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServiceOrderService {
    private final ServiceOrderRepository repo;
    private final ServiceOrderMapper mapper = ServiceOrderMapper.INSTANCE;

    public ServiceOrderService(ServiceOrderRepository repo) {
        this.repo = repo;
    }

    /**
     * category 값만 바꿔서 조회
     */
    public List<ServiceOrderResponseDto> getItemsByCategory(String category) {
        return repo.findAllByCategory(category).stream()
                .map(ServiceOrderResponseDto::new)
                .toList();
    }
}