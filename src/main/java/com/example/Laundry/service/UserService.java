package com.example.Laundry.service;

import com.example.Laundry.domain.User;
import com.example.Laundry.dto.UserCreateDto;
import com.example.Laundry.dto.UserResponseDto;
import com.example.Laundry.mapper.UserMapper;
import com.example.Laundry.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User 도메인 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * 회원 가입
     */
    public UserResponseDto register(UserCreateDto dto) {
        User entity = userMapper.toEntity(dto);
        User saved = userRepository.save(entity);
        return userMapper.toDto(saved);
    }

    /**
     * 전체 회원 조회
     */
    @Transactional(readOnly = true)
    public List<UserResponseDto> listAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * ID로 회원 조회
     */
    @Transactional(readOnly = true)
    public UserResponseDto findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return userMapper.toDto(user);
    }

    /**
     * 회원 정보 수정
     */
    public UserResponseDto update(String id, UserCreateDto dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        // 필드 업데이트
        existing.setName(dto.name());
        existing.setEmail(dto.email());
        existing.setPwd(dto.pwd());
        existing.setAddr(dto.addr());
        existing.setPhone(dto.phone());
        existing.setCountryCode(dto.countryCode());
        existing.setdialCode(dto.dialCode());
        existing.setManager(dto.manager());
        existing.setProfile(dto.profile());
        User updated = userRepository.save(existing);
        return userMapper.toDto(updated);
    }

    /**
     * 회원 탈퇴
     */
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }
}
