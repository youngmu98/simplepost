package com.simpo.simplepost.comment.controller;

import com.simpo.simplepost.comment.dto.CommentCreateDto;
import com.simpo.simplepost.comment.dto.CommentPatchDto;
import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.comment.mapper.CommentMapper;
import com.simpo.simplepost.comment.service.CommentService;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final CommentMapper commentMapper;

    public CommentController(CommentService commentService, PostService postService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.postService = postService;
        this.commentMapper = commentMapper;
    }

    // 댓글 생성
    @PostMapping("/add")
    public String createComment(@RequestParam Long postId, @ModelAttribute CommentCreateDto commentCreateDto, Model model) {
        Comment comment = commentMapper.commentCreateDtoToComment(commentCreateDto);
        Comment createComment = commentService.createComment(comment, postId);
        return "redirect:/post/" + createComment.getPost().getId();
    }

    // 댓글 수정
    @PostMapping("/{commentId}/edit")
    public String editComment(@PathVariable Long commentId, @ModelAttribute CommentPatchDto commentPatchDto) {
        Comment comment = commentMapper.commentPatchDtoToComment(commentPatchDto);
        Long postId = commentService.editComment(commentId, comment);
        return "redirect:/post/" + postId;
    }

    // 댓글 삭제
    @DeleteMapping("/delete")
    public String deleteComment(@RequestParam Long commentId) {
        Long postId = commentService.deleteComment(commentId);
        return "redirect:/post/" + postId;
    }
}
