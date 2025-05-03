package com.example.Laundry.service;

import com.example.Laundry.domain.User;
import com.example.Laundry.dto.UserCreateDto;
import com.example.Laundry.dto.UserResponseDto;
import com.example.Laundry.mapper.UserMapper;
import com.example.Laundry.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;    // 추가
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {    // ← 여기에 implements UserDetailsService 추가

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원 가입
     */
    public UserResponseDto register(UserCreateDto dto) {
        // 1. DTO에서 평문 비밀번호를 꺼내 해싱
        String encodedPwd = passwordEncoder.encode(dto.pwd());

        // 2. 엔티티로 매핑할 때 해시된 비밀번호로 교체
        User entity = userMapper.toEntity(dto);
        entity.setPwd(encodedPwd);
        entity.setRegdate(LocalDateTime.now());

        // 3. 저장 및 DTO 반환
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

    /**
     * 아이디 중복 검사
     * @param inputId 조회할 아이디
     * @return true면 이미 존재, false면 사용 가능
     */
    public boolean checkId(String inputId) {
        return userRepository.existsById(inputId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(entity.getId())
                .password(entity.getPwd())      // DB에 이미 해시된 비밀번호
                .authorities("ROLE_USER")       // 필요시 권한 추가
                .build();
    }
}
