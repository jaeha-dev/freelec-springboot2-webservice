package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 등록 요청 Dto 클래스
 * @memo   : Entity 클래스를 요청/응답 클래스로 사용하지 않는다.
 *           (Entity 와 Dto 는 반드시 역할을 분리하여 사용한다.)
 */
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /**
     * Entity 클래스로 매핑한다.
     * @return : Posts
     */
    public Posts toEntity() {
        return Posts.builder().title(title).content(content).author(author).build();
    }
}