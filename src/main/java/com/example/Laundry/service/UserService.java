package com.example.Laundry.service;

import com.example.Laundry.config.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;

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

    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /** 아이디 중복 여부 */
    @Transactional(readOnly = true)
    public boolean idExists(String id) {
        return userRepository.existsById(id);
    }


    /**
     * 로그인 로직: 아이디와 비밀번호 검사
     * @param id       사용자 아이디
     * @param rawPwd   입력된 평문 비밀번호
     * @return true면 인증 성공, false면 인증 실패
     */
    @Transactional(readOnly = true)
    public boolean authenticate(String id, String rawPwd) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
        // matches: rawPwd를 저장된 해시와 비교
        return passwordEncoder.matches(rawPwd, user.getPwd());
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

        existing.setName(dto.name());
        existing.setEmail(dto.email());
        // 비밀번호 변경 시 해싱
        if (dto.pwd() != null && !dto.pwd().isEmpty()) {
            existing.setPwd(passwordEncoder.encode(dto.pwd()));
        }
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
                .password(entity.getPwd())
                .authorities("ROLE_USER")
                .build();
    }

    /**
     * 아이디 찾기
     * @param name 조회할 이름
     * @param email 조회할 이메일
     */
    public UserResponseDto findByNameAndEmail(String name, String email) {
        return userRepository.findByNameAndEmail(name, email)
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getPwd(),
                        user.getName(),
                        user.getEmail(),
                        user.getAddr(),
                        user.getPhone(),
                        user.getCountryCode(),
                        user.getDialCode(),
                        user.getManager(),
                        user.getProfile(),
                        user.getRegdate()
                ))
                .orElse(null);
    }

    /**
     * 비밀번호 찾기
     * @param id 조회할 아이디
     * @param name 조회할 이름
     * @param email 조회할 이메일
     */
    public boolean existsByIdAndNameAndEmail(String id, String name, String email) {
        return userRepository.existsByIdAndNameAndEmail(id, name, email);
    }

    /**
     * 비밀번호 재설정 로직
     * @param id      아이디(username)
     * @param rawPwd  평문 비밀번호
     * @return 성공 시 true, 실패 시 false
     */
    public boolean updatePassword(String id, String rawPwd) {
        return userRepository.findById(id)
                .map(user -> {
                    // JPA 영속성 컨텍스트 내에서 dirty-checking으로 자동 반영
                    user.setPwd(passwordEncoder.encode(rawPwd));
                    return true;
                })
                .orElse(false);
    }

    /**
     * 즉시 새 JWT 토큰 발급
     * @param userId 아이디(PK)
     */
    public String generateNewJwt(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + userId)
                );
        return jwtTokenProvider.createToken(
                user.getId(),
                List.of("ROLE_USER")
        );
    }

    /**
     * 프로필 저장
     * @param userId 아이디(PK)
     * @param imagePath 이미지 경로
     */
    public void updateProfileImage(String userId, String imagePath) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음: " + userId));

        user.setProfile(imagePath);
        userRepository.save(user);
    }
}
