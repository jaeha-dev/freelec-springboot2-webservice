package com.jojoldu.book.springboot.config.oauth.dto;

import com.jojoldu.book.springboot.domain.user.User;
import lombok.Getter;
import java.io.Serializable;

/**
 * @author : jaeha-dev (Git)
 * @title  : 인증된 사용자 정보 Dto 클래스
 * @memo   : User 엔티티를 사용하지 않는다.
 *           (세션에 저장하므로 직렬화 기능을 추가한 Dto 클래스를 추가한다.)
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}