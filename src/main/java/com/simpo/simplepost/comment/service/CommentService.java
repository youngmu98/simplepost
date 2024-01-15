package com.simpo.simplepost.comment.service;

import com.simpo.simplepost.comment.dto.CommentCreateDto;
import com.simpo.simplepost.comment.dto.CommentPatchDto;
import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.comment.repository.CommentRepository;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment createComment(CommentCreateDto commentCreateDto) {
        Comment comment = commentCreateDto.toEntity();
        Post post = postRepository.findById(commentCreateDto.getPostId()).orElse(null);
        comment.addPost(post);

        commentRepository.save(comment);

        return comment;
    }

    public List<Comment> findCommentsByPost(Post post) {
        return commentRepository.findAllByPostOrderByCreatedAtDesc(post);
    }

    public Long deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        commentRepository.delete(comment);
        return comment.getPost().getId();
    }

    public Comment editComment(Long commentId, CommentPatchDto commentPatchDto) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        comment.updateContent(commentPatchDto.getContent());
        commentRepository.save(comment);
        return comment;
    }
}
