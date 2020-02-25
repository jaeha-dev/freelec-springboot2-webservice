package com.jojoldu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 도메인 클래스
 * @memo   : Entity 클래스는 Setter 메소드를 생성하지 않는다.
 *           (인스턴스 값의 변경에는 의도와 목적이 명확해야 한다.
 *           값 변경이 필요할 경우, 발생한 이벤트에 적합한 public 메소드를 정의 & 호출한다.)
 */
@Getter
@NoArgsConstructor
@Entity
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 기본 키, Auto Increment

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 컬럼 타입을 Text 타입으로 지정

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /**
     * 게시글 수정
     * (Entity 객체의 값을 변경하면 Update 쿼리가 불필요하다.)
     * @param title   : 게시글 제목
     * @param content : 게시글 내용
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}