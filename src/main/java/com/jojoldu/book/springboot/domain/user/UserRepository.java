package com.jojoldu.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * @author : jaeha-dev (Git)
 * @title  : 계정 레포지토리 클래스
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // 중복 이메일 검사를 위한 조회 메소드
}