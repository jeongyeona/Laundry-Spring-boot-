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

    public List<ServiceOrderResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ServiceOrderResponseDto create(ServiceOrderCreateDto dto) {
        ServiceOrder entity = mapper.toEntity(dto);
        entity.setRegdate(dto.regdate());
        ServiceOrder saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public ServiceOrderResponseDto findById(Integer code) {
        ServiceOrder entity = repo.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("ServiceOrder not found: " + code));
        return mapper.toDto(entity);
    }

    public ServiceOrderResponseDto update(Integer code, ServiceOrderCreateDto dto) {
        ServiceOrder existing = repo.findById(code)
                .orElseThrow(() -> new IllegalArgumentException("ServiceOrder not found: " + code));
        existing.setOrderer(dto.orderer());
        existing.setCategory(dto.category());
        existing.setOrderPrice(dto.orderPrice());
        existing.setOrderAddr(dto.orderAddr());
        existing.setRegdate(LocalDate.now());
        existing.setReservationDate(dto.reservationDate());
        existing.setRequest(dto.request());
        existing.setState(dto.state());
        existing.setGetInvoiceNum(dto.getInvoiceNum());
        existing.setSendInvoiceNum(dto.sendInvoiceNum());
        ServiceOrder updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer code) {
        if (!repo.existsById(code)) {
            throw new IllegalArgumentException("ServiceOrder not found: " + code);
        }
        repo.deleteById(code);
    }
}