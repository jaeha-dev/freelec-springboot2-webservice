package com.jojoldu.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 레포지토리 테스트 클래스
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(PostsRepositoryTest.class);

    @Autowired private PostsRepository postsRepository;

    /**
     * 각 테스트 종료 후, 테스트 데이터 삭제
     * (배포 전, 전체 테스트를 수행할 때 테스트간 데이터 침범을 방지하기 위해 사용한다.)
     */
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_등록_및_조회() {
        // Given
        String title = "제목";
        String content = "내용";
        String author = "작성자";
        // ID 값이 없을 경우, insert (있을 경우, update)
        postsRepository.save(Posts.builder().title(title).content(content).author(author).build());

        // When
        List<Posts> postsList = postsRepository.findAll();

        // Then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
        assertThat(posts.getAuthor()).isEqualTo(author);
    }

    @Test
    public void Auditing_테스트() {
        // Given
        LocalDateTime now = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        postsRepository.save(Posts.builder().title("제목").content("내용").author("작성자").build());

        // When
        List<Posts> postsList = postsRepository.findAll();

        // Then
        Posts posts = postsList.get(0);
        logger.info("getCreateDate: " + posts.getCreateDate() + " / getModifiedDate:" + posts.getModifiedDate());
        assertThat(posts.getCreateDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}