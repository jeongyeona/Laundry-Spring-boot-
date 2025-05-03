package com.example.Laundry.service;

import com.example.Laundry.domain.CountryPhone;
import com.example.Laundry.dto.CountryPhoneCreateDto;
import com.example.Laundry.dto.CountryPhoneResponseDto;
import com.example.Laundry.mapper.CountryPhoneMapper;
import com.example.Laundry.repository.CountryPhoneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryPhoneService {

    private final CountryPhoneRepository repo;
    private final com.example.Laundry.mapper.CountryPhoneMapper mapper;

    public CountryPhoneService(CountryPhoneRepository repo,
                               CountryPhoneMapper mapper) {
        this.repo   = repo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<CountryPhoneResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}