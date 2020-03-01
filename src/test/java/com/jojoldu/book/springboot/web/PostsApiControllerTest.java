package com.jojoldu.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 API 컨트롤러 테스트 클래스
 * @memo   : @WebMvcTest 는 JPA 기능이 동작하지 않으므로 @SpringBootTest 를 사용하였다.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort private int port;
    @Autowired private TestRestTemplate restTemplate;
    @Autowired private PostsRepository postsRepository;

    @Autowired private WebApplicationContext context; // MockMvc 를 사용한 테스트를 위해 추가
    private MockMvc mockMvc;

    @Before
    public void setup() {
        // @WithMockUser 는 MockMvc 에서만 동작한다.
        // 각 테스트 시, MockMvc 인스턴스를 생성하도록 한다.
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @After
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 게시글_등록() throws Exception {
        // Given
        String title = "제목";
        String content = "내용";
        String author = "작성자";
        String uri = "http://localhost:" + port + "/api/v1/posts";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author(author).build();

        // When
        // ResponseEntity<Long> responseEntity = restTemplate.postForEntity(uri, requestDto, Long.class);

        // Then
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L); // 등록된 게시글 번호는 0보다 크다.

        // When
        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_UTF8)
                                 .content(new ObjectMapper().writeValueAsString(requestDto)))
                                 .andExpect(status().isOk());

        // Then
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
        assertThat(postsList.get(0).getAuthor()).isEqualTo(author);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 게시글_수정() throws Exception {
        // Given
        // 수정될 게시글 등록
        Posts savedPosts = postsRepository.save(Posts.builder().title("제목").content("내용").author("작성자").build());

        Long updateId = savedPosts.getId(); // 수정될 게시글 번호
        String expectedTitle = "수정된 제목";
        String expectedContent = "수정된 내용";
        String uri = "http://localhost:" + port + "/api/v1/posts/" + updateId;
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

        // 요청 Entity 생성 (수정할 게시글 정보)
        // HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        // When
        // ResponseEntity<Long> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, Long.class);

        // Then
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseEntity.getBody()).isGreaterThan(0L);

        // When
        mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(new ObjectMapper().writeValueAsString(requestDto)))
                                .andExpect(status().isOk());

        // Then
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(postsList.get(0).getContent()).isEqualTo(expectedContent);
    }
}