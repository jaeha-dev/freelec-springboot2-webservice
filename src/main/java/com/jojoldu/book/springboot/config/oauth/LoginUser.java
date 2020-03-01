package com.jojoldu.book.springboot.config.oauth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : jaeha-dev (Git)
 * @title  : 세션 값 어노테이션
 * @memo   : 세션을 가져오는 코드를 어노테이션으로 지정하여 간편화 및 중복 코드 최소화
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}