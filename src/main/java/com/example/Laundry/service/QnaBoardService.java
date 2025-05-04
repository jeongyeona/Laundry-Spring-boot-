// Service: com.example.Laundry.service.QnaBoardService.java
package com.example.Laundry.service;

import com.example.Laundry.domain.QnaBoard;
import com.example.Laundry.dto.QnaBoardCreateDto;
import com.example.Laundry.dto.QnaBoardResponseDto;
import com.example.Laundry.mapper.QnaBoardMapper;
import com.example.Laundry.repository.QnaBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class QnaBoardService {
    private final QnaBoardRepository repo;
    private final QnaBoardMapper mapper = QnaBoardMapper.INSTANCE;

    public QnaBoardService(QnaBoardRepository repo) {
        this.repo = repo;
    }

    public List<QnaBoardResponseDto> listAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public QnaBoardResponseDto create(QnaBoardCreateDto dto) {
        QnaBoard entity = mapper.toEntity(dto);
        entity.setRegdate(dto.regdate());
        QnaBoard saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    public QnaBoardResponseDto findById(Integer num) {
        QnaBoard entity = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("QnA not found: " + num));
        return mapper.toDto(entity);
    }

    public QnaBoardResponseDto update(Integer num, QnaBoardCreateDto dto) {
        QnaBoard existing = repo.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("QnA not found: " + num));
        existing.setWriter(dto.writer());
        existing.setTitle(dto.title());
        existing.setContent(dto.content());
        existing.setRegdate(LocalDate.now());
        existing.setOrgFileName(dto.orgFileName());
        existing.setSaveFileName(dto.saveFileName());
        existing.setFileSize(dto.fileSize());
        existing.setCheckReply(dto.checkReply());
        QnaBoard updated = repo.save(existing);
        return mapper.toDto(updated);
    }

    public void delete(Integer num) {
        if (!repo.existsById(num)) {
            throw new IllegalArgumentException("QnA not found: " + num);
        }
        repo.deleteById(num);
    }
}
