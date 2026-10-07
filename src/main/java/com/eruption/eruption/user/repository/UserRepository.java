package com.eruption.eruption.user.repository;

import com.eruption.eruption.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);   // 메서드 이름으로 쿼리 생성
    boolean existsByEmail(String email);        // 이메일 중복 확인
}
