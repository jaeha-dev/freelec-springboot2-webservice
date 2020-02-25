package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author : jaeha-dev (Git)
 * @title  : 실습용 컨트롤러 테스트 클래스
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    // @RunWith: 테스트 진행 시, JUnit 에 내장된 실행자 대신 SpringRunner 실행자를 사용한다.
    //           SpringRunner 는 스프링 부트와 JUnit 사이에서 연결자 역할을 수행한다.

    // @WebMvcTest: Web(Spring MVC)에 집중하는 테스트 어노테이션이다.
    //              @Controller, @ControllerAdvice 등의 어노테이션 사용 가능.
    //              @Service, @Component, @Repository 등의 어노테이션 사용 불가능.

    @Autowired private MockMvc mockMvc;

    @Test
    public void 문자열_Hello_가_반환된다() throws Exception {
        String hello = "Hello";

        mockMvc.perform(get("/hello"))
               .andExpect(status().isOk())
               .andExpect(content().string(hello));
    }

    @Test
    public void 객체_DTO_가_반환된다() throws Exception {
        String name = "테스트";
        int amount = 200;

        mockMvc.perform(get("/hello/dto")
               .param("name", name)
               .param("amount", String.valueOf(amount)))

               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name", is(name)))
               .andExpect(jsonPath("$.amount", is(amount)));
    }
}