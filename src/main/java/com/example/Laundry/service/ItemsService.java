package com.example.Laundry.service;

import com.example.Laundry.domain.Items;
import com.example.Laundry.dto.ItemsCreateDto;
import com.example.Laundry.dto.ItemsResponseDto;
import com.example.Laundry.mapper.ItemsMapper;
import com.example.Laundry.repository.ItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Items 엔티티 관련 비즈니스 로직 처리 서비스
 */
@Service
@Transactional
public class ItemsService {

    private final ItemsRepository itemsRepository;
    private final ItemsMapper itemsMapper;

    public ItemsService(ItemsRepository itemsRepository, ItemsMapper itemsMapper) {
        this.itemsRepository = itemsRepository;
        this.itemsMapper = itemsMapper;
    }

    /**
     * 새로운 Items 생성
     */
    public ItemsResponseDto create(ItemsCreateDto dto) {
        Items entity = itemsMapper.toEntity(dto);
        Items saved = itemsRepository.save(entity);
        return itemsMapper.toDto(saved);
    }

    /**
     * 모든 Items 조회
     */
    @Transactional(readOnly = true)
    public List<ItemsResponseDto> listAll() {
        return itemsRepository.findAll().stream()
                .map(itemsMapper::toDto)
                .toList();
    }

    /**
     * 지정한 category 의 Items 조회
     */
    @Transactional(readOnly = true)
    public List<ItemsResponseDto> findByCategory(String category) {
        // 예: JPA 레포지토리에 findAllByCategory 메서드가 있어야 합니다.
        List<Items> all = itemsRepository.findAllByCategory(category);
        // 엔티티 리스트를 DTO 리스트로 매핑
        return all.stream()
                .map(itemsMapper::toDto)
                .collect(Collectors.toList());
    }
}
