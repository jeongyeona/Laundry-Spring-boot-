package com.example.Laundry.repository;

import com.example.Laundry.domain.Categorize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorizeRepository extends JpaRepository<Categorize, String> {
}
