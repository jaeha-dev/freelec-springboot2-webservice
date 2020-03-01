package com.jojoldu.book.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author : jaeha-dev (Git)
 * @title  : JPA 설정 클래스
 * @memo   : JPA Auditing 활성화
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}