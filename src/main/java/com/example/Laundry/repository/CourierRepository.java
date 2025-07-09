package com.example.Laundry.repository;

import com.example.Laundry.domain.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    List<Courier> findByEnabledTrue();
}