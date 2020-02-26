package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author : jaeha-dev (Git)
 * @title  : 메인 클래스
 * @memo   : JPA Auditing 활성화
 */
@EnableJpaAuditing
@SpringBootApplication
public class Application {
    // @SpringBootApplication: 스프링 부트의 자동 설정, 스프링 빈 읽기/생성이 모두 자동으로 설정된다.

    public static void main(String[] args) {

        // 내장 WAS 실행 (내장 WAS 라 하여 성능상 이슈가 있는 것은 아니다.)
        SpringApplication.run(Application.class, args);
    }
}