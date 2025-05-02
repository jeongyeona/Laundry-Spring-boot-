package com.example.Laundry.repository;

import com.example.Laundry.domain.CountryPhone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryPhoneRepository extends JpaRepository<CountryPhone, String> {
    // 필요한 커스텀 메서드 추가 가능
}
