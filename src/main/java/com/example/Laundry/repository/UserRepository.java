package com.example.Laundry.repository;

import com.example.Laundry.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByNameAndEmail(String name, String email);
    boolean existsByIdAndNameAndEmail(String id, String name, String email);
    Page<User> findByIdContaining(String id, Pageable pageable);
    Page<User> findByIdContainingAndManager(String id, String manager, Pageable pageable);
    Page<User> findByNameContaining(String name, Pageable pageable);
    Page<User> findByNameContainingAndManager(String name, String manager, Pageable pageable);
    Page<User> findByManager(String manager, Pageable pageable);

}