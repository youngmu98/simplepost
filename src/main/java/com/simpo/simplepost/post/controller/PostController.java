package com.simpo.simplepost.post.controller;

import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.service.BoardService;
import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.comment.service.CommentService;
import com.simpo.simplepost.post.dto.PostCreateDto;
import com.simpo.simplepost.post.dto.PostPatchDto;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.mapper.PostMapper;
import com.simpo.simplepost.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final PostMapper postMapper;

    public PostController(PostService postService, BoardService boardService, CommentService commentService, PostMapper postMapper) {
        this.postService = postService;
        this.boardService = boardService;
        this.commentService = commentService;
        this.postMapper = postMapper;
    }

    // 게시글 생성폼
    @GetMapping("/{boardId}/posts/add")
    public String getPostCreateView(Model model, @PathVariable Long boardId) {
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "post/addPostForm";
    }

    // 게시글 생성
    @PostMapping("/{boardId}/posts/add")
    public String createPost(@PathVariable Long boardId, @ModelAttribute PostCreateDto postCreateDto, RedirectAttributes redirectAttributes) {
        Post post = postMapper.PostCreateDtoToPost(postCreateDto);
        postService.addPost(post, boardId);
        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/boards/{boardId}";
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public String deletePost(@PathVariable Long postId, RedirectAttributes redirectAttributes) {
        Long boardId = postService.deletePostById(postId);
        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/boards/{boardId}";
    }

    // 게시글 수정폼
    @GetMapping("/posts/edit/{postId}")
    public String getPostEditView(@PathVariable Long postId, Model model) {
        Post post = postService.findByPostId(postId);
        model.addAttribute("post", post);
        return "post/editPostForm";
    }

    // 게시글 수정
    @PostMapping("/posts/edit/{postId}")
    public String updatePost(@PathVariable Long postId, @ModelAttribute PostPatchDto postPatchDto) {
        Post editpost = postMapper.PostPatchDtoToPost(postPatchDto);
        Post post = postService.updatePost(editpost, postId);
        return "redirect:/boards/" + post.getBoard().getId();
    }

    // 게시글 상세페이지
    @GetMapping("/post/{postId}")
    public String getPostDetail(@PathVariable Long postId, Model model) {
        Post post = postService.findByPostId(postId);
        List<Comment> comments = commentService.findCommentsByPost(post);
        Long boardId = post.getBoard().getId();
        model.addAttribute("post", post);
        model.addAttribute("boardId", boardId);
        model.addAttribute("comments", comments);
        return "/post/post";
    }
}
