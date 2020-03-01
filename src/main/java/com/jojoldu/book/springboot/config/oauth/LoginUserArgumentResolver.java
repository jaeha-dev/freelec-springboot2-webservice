package com.jojoldu.book.springboot.config.oauth;

import com.jojoldu.book.springboot.config.oauth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpSession;

/**
 * @author : jaeha-dev (Git)
 * @title  : LoginUser 어노테이션을 사용하기 위한 리졸버 클래스
 */
@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    /**
     * 컨트롤러 메소드의 특정 인자를 지원하는지 판별한다.
     * @param parameter : 컨트롤러 메소드로 전달된 매개변수
     * @return          : @LoginUser 인자를 지원하고 SessionUser 매개변수가 전달되었을 때, T
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @LoginUser 어노테이션이 있을 경우, T
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;

        // 매개변수 타입이 SessionUser 일 경우, T
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    /**
     * 매개변수에 전달할 객체를 생성한다.
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        // 세션에서 객체를 가져온다.
        return httpSession.getAttribute("user");
    }
}