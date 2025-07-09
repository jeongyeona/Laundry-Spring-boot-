package com.example.Laundry.service;

import com.example.Laundry.domain.Courier;
import com.example.Laundry.repository.CourierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierService {
    private final CourierRepository courierRepository;

    public CourierService(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    public List<Courier> getAllCouriers() {
        return courierRepository.findByEnabledTrue(); // 또는 findAll()
    }
}
