package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.oauth.LoginUser;
import com.jojoldu.book.springboot.config.oauth.dto.SessionUser;
import com.jojoldu.book.springboot.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpSession;

/**
 * @author : jaeha-dev (Git)
 * @title  : 인덱스 컨트롤러 클래스
 */
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PostsService postsService;
    // private final HttpSession httpSession;

    /**
     * 인덱스
     * @param model       : 모델
     * @param sessionUser : 세션
     * @return            : 인덱스 뷰
     */
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser sessionUser) {
        model.addAttribute("posts", postsService.findAllDesc());
        // 개선 전 (@LoginUser 사용 전)
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        // if (user != null) model.addAttribute("userName", user.getName());

        // 개선 후
        if (sessionUser != null) model.addAttribute("userName", sessionUser.getName());

        return "index";
    }

    /**
     * 게시글 등록
     * @return : 게시글 등록 뷰
     */
    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    /**
     * 게시글 수정
     * @param id    : 게시글 번호
     * @param model : 모델
     * @return      : 게시글 수정 뷰
     */
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("post", postsService.findById(id));
        return "posts-update";
    }
}