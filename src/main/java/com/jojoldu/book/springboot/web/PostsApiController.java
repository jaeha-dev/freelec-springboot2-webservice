package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsListResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author : jaeha-dev (Git)
 * @title  : 게시글 API 컨트롤러 클래스
 */
@RestController
@RequiredArgsConstructor
public class PostsApiController {
    private final PostsService postsService;

    /**
     * 게시글 목록 조회
     * @return : 조회된 게시글 목록
     */
    @GetMapping("/api/v1/posts")
    public List<PostsListResponseDto> findAllDesc() {
        return postsService.findAllDesc();
    }

    /**
     * 게시글 상세 조회
     * @param id : 게시글 번호
     * @return   : 조회된 게시글
     */
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable("id") Long id) {
        return postsService.findById(id);
    }

    /**
     * 게시글 등록
     * @param requestDto : 게시글 정보
     * @return           : 등록된 게시글 번호
     */
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    /**
     * 게시글 수정
     * @param id         : 게시글 번호
     * @param requestDto : 게시글 정보
     * @return           : 수정된 게시글 번호
     */
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id,
                       @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    /**
     * 게시글 삭제
     * @param id : 게시글 번호
     * @return   : 삭제된 게시글 번호
     */
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}