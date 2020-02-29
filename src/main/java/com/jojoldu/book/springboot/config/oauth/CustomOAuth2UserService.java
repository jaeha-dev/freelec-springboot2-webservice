package com.jojoldu.book.springboot.config.oauth;

import com.jojoldu.book.springboot.config.oauth.dto.OAuthAttributes;
import com.jojoldu.book.springboot.config.oauth.dto.SessionUser;
import com.jojoldu.book.springboot.domain.user.User;
import com.jojoldu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * @author : jaeha-dev (Git)
 * @title  : 커스텀 OAuth2 계정 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    /**
     *
     * @param userRequest : 소셜 로그인 요청
     * @return            : OAuth2User
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 소셜 서비스를 구분하는 코드(구글, 네이버 등)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시, 키가 되는 필드 값
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService 를 통해 가져온 OAuth2User 의 속성을 담을 객체
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // DB 및 세션에 사용자 정보 저장
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                                                           attributes.getAttributes(),
                                                           attributes.getNameAttributeKey());
    }

    /**
     * 구글 사용자 정보를 저장하거나 갱신한다.
     * @param attributes : OAuthAttributes
     * @return           : User
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        // 구글 사용자 정보(이름, 사진)이 변경되었을 경우, 갱신한다.
        User user = userRepository.findByEmail(attributes.getEmail())
                                  .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                                  // 등록된 이메일이 아닐 경우, User 엔티티 생성
                                  .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}