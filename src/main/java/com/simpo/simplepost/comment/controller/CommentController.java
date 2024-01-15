package com.simpo.simplepost.comment.controller;

import com.simpo.simplepost.comment.dto.CommentCreateDto;
import com.simpo.simplepost.comment.dto.CommentPatchDto;
import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.comment.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add")
    public String createComment(@RequestParam Long postId, Model model, @ModelAttribute CommentCreateDto commentCreateDto) {
        commentCreateDto.setPostId(postId);
        Comment comment = commentService.createComment(commentCreateDto);

        return "redirect:/post/" + comment.getPost().getId();
    }

    @PostMapping("/{commentId}/edit")
    public String editComment(@PathVariable Long commentId, @ModelAttribute CommentPatchDto commentPatchDto) {
        Comment editComment = commentService.editComment(commentId, commentPatchDto);
        Long postId = editComment.getPost().getId();
        return "redirect:/post/" +postId;
    }

    @DeleteMapping("/delete")
    public String deleteComment(@RequestParam Long commentId) {
        Long postId = commentService.deleteComment(commentId);
        return "redirect:/post/" + postId;
    }
}
