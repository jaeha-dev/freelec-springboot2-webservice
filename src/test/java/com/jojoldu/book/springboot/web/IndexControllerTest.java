package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.domain.posts.PostsRepositoryTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author : jaeha-dev (Git)
 * @title  : 인덱스 컨트롤러 테스트 클래스
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(PostsRepositoryTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void 인덱스_화면() {
        // When
        String body = this.restTemplate.getForObject("/", String.class);

        // Then
        logger.info(body);
        assertThat(body).contains("스프링 부트로 시작하는 웹 서비스");
    }
}