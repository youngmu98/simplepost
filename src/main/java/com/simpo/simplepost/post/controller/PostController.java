package com.simpo.simplepost.post.controller;

import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.service.BoardService;
import com.simpo.simplepost.post.dto.PostCreateDto;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.service.PostService;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final BoardService boardService;

    public PostController(PostService postService, BoardService boardService) {
        this.postService = postService;
        this.boardService = boardService;
    }

    @GetMapping("/{boardId}/posts/add")
    public String springStart(Model model, @PathVariable Long boardId){
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "post/addPostForm";
    }

    @PostMapping("/{boardId}/posts/add")
    public String createPost(@PathVariable Long boardId, @ModelAttribute PostCreateDto postCreateDto, RedirectAttributes redirectAttributes){
        postCreateDto.setBoardId(boardId);
        postService.addPost(postCreateDto);
        redirectAttributes.addAttribute("boardId",boardId);
        return "redirect:/boards/{boardId}";
    }


}
