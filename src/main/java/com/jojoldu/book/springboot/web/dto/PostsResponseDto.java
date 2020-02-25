package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.domain.posts.Posts;
import lombok.Getter;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 응답 Dto 클래스
 * @memo   : Entity 클래스를 요청/응답 클래스로 사용하지 않는다.
 *           (Entity 와 Dto 는 반드시 역할을 분리하여 사용한다.)
 */
@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}