package com.jojoldu.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @title  : 실습용 Response Dto 클래스
 * @author : jaeha-dev (Git)
 */
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    // @RequiredArgsConstructor: 선언된 모든 final 필드가 포함된 생성자를 생성한다.
    //                           (final 이 아닌 필드는 생성자에 포함되지 않는다.)

    private final String name;
    private final int amount;
}