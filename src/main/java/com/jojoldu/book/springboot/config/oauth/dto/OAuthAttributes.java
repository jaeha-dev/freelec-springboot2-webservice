package com.jojoldu.book.springboot.config.oauth.dto;

import com.jojoldu.book.springboot.domain.user.Role;
import com.jojoldu.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;

/**
 * @author : jaeha-dev (Git)
 * @title  : OAuth2 속성 Dto 클래스
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    /**
     *
     * @param registrationId        : 현재 로그인 진행 중인 소셜 서비스를 구분하는 코드(구글, 네이버 등)
     * @param userNameAttributeName : OAuth2 로그인 진행 시, 키가 되는 필드 값
     * @param attributes            : OAuth2User 에서 반환하는 사용자 정보
     * @return                      : 하위 메소드 호출
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    /**
     *
     * @param userNameAttributeName : OAuth2 로그인 진행 시, 키가 되는 필드 값
     * @param attributes            : OAuth2User 에서 반환하는 사용자 정보
     * @return                      : OAuthAttributes
     */
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                              .name((String) attributes.get("name"))
                              .email((String) attributes.get("email"))
                              .picture((String) attributes.get("picture"))
                              .attributes(attributes)
                              .nameAttributeKey(userNameAttributeName)
                              .build();
    }

    public User toEntity() {
        return User.builder()
                   .name(name)
                   .email(email)
                   .picture(picture)
                   .role(Role.GUEST)
                   .build();
    }
}