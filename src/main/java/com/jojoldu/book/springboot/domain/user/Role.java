package com.jojoldu.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author : jaeha-dev (Git)
 * @title  : 계정 권한 도메인 클래스
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}