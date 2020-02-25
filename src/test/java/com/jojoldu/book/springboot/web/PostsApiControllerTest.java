package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 API 컨트롤러 테스트 클래스
 * @memo   : @WebMvcTest 는 JPA 기능이 동작하지 않으므로 @SpringBootTest 를 사용하였다.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_등록() {
        // Given
        String title = "제목";
        String content = "내용";
        String author = "작성자";
        String uri = "http://localhost:" + port + "/api/v1/posts";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author(author).build();

        // When
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(uri, requestDto, Long.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L); // 등록된 게시글 번호는 0보다 크다.

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
        assertThat(postsList.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    public void 게시글_수정() {
        // Given
        // 수정될 게시글 등록
        Posts savedPosts = postsRepository.save(Posts.builder().title("제목").content("내용").author("작성자").build());

        Long updateId = savedPosts.getId(); // 수정될 게시글 번호
        String expectedTitle = "수정된 제목";
        String expectedContent = "수정된 내용";
        String uri = "http://localhost:" + port + "/api/v1/posts/" + updateId;
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        // 요청 Entity 생성 (수정할 게시글 정보)
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // When
        ResponseEntity<Long> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Long.class);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(postsList.get(0).getContent()).isEqualTo(expectedContent);
    }
}