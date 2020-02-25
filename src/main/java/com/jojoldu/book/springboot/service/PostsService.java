package com.jojoldu.book.springboot.service;

import com.jojoldu.book.springboot.domain.posts.Posts;
import com.jojoldu.book.springboot.domain.posts.PostsRepository;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class PostsService {
    private final PostsRepository postsRepository;

    public PostsResponseDto findById(Long id) {
        Posts posts =  postsRepository.findById(id)
                                      .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return new PostsResponseDto(posts);
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        // save() 수행 결과, 등록 요청한 게시글이 반환된다.
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                                     .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        // JPA 의 영속성 컨텍스트(Entity 를 영구 저장하는 환경)로 인해 쿼리를 수행할 필요가 없다.
        // (트랜잭션 내에서 DB 로부터 데이터를 가져오면 영속성 컨텍스트가 유지되며,
        // 이 상태에서 데이터를 변경할 경우, 트랜잭션이 종료되는 시점에서 DB 에 변경된 데이터가 반영된다.)
        // 즉, Entity 객체의 값만 변경하면 Update 쿼리가 불필요하다. (이를 더티 체킹이라 한다.)
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
}