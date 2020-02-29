package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 레포지토리 클래스
 * @memo   : SQL 매핑에서의 Dao 와 유사한 DB 계층 접근자이다.
 *           (Entity 클래스와 Repository 인터페이스는 동일한 패키지 내에서 관리한다.)
 */
@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {

    // 기본 CRUD 메소드
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}