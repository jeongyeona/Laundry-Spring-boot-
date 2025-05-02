package com.example.Laundry.service;

import com.example.Laundry.domain.Items;
import com.example.Laundry.dto.ItemsCreateDto;
import com.example.Laundry.dto.ItemsResponseDto;
import com.example.Laundry.mapper.ItemsMapper;
import com.example.Laundry.repository.ItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * 지정한 inum 의 Items 조회
     */
    @Transactional(readOnly = true)
    public ItemsResponseDto findById(Long inum) {
        Items items = itemsRepository.findById(inum)
                .orElseThrow(() -> new IllegalArgumentException("Items not found: " + inum));
        return itemsMapper.toDto(items);
    }

    /**
     * 지정한 inum 의 Items 정보 수정
     */
    public ItemsResponseDto update(Long inum, ItemsCreateDto dto) {
        Items existing = itemsRepository.findById(inum)
                .orElseThrow(() -> new IllegalArgumentException("Items not found: " + inum));
        // 필드 업데이트
        existing.setCategory(dto.category());
        existing.setItem(dto.item());
        existing.setPrice(dto.price());
        Items updated = itemsRepository.save(existing);
        return itemsMapper.toDto(updated);
    }

    /**
     * 지정한 inum 의 Items 삭제
     */
    public void delete(Long inum) {
        if (!itemsRepository.existsById(inum)) {
            throw new IllegalArgumentException("Items not found: " + inum);
        }
        itemsRepository.deleteById(inum);
    }
}
