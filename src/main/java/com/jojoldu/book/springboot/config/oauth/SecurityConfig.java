package com.jojoldu.book.springboot.config.oauth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author : jaeha-dev (Git)
 * @title  : 보안 설정 클래스
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // H2 웹 콘솔을 사용하기 위해 CSRF, 프레임 옵션 비활성화
        http.csrf().disable()
            .headers().frameOptions().disable()
            .and()
                // URL 별 권한 지정 (정적 리소스, H2 웹 콘솔은 전체 열람, API 는 USER 권한 사용자만, 나머지는 인증된 사용자)
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
            .and()
                .logout().logoutSuccessUrl("/")
            .and()
                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 -> 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);

    }
}