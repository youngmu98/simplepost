package com.simpo.simplepost.comment.service;

import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.comment.repository.CommentRepository;
import com.simpo.simplepost.global.exception.ExceptionCode;
import com.simpo.simplepost.global.exception.ServiceLogicException;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public Comment createComment(Comment comment, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ServiceLogicException(ExceptionCode.POST_NOT_FOUND));
        comment.addPost(post);
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> findCommentsByPost(Post post) {
        return commentRepository.findAllByPostOrderByCreatedAtDesc(post);
    }

    public Long deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ServiceLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
        return comment.getPost().getId();
    }

    public Long editComment(Long commentId, Comment comment) {
        Comment editComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ServiceLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        Optional.ofNullable(comment.getContent()).ifPresent(content -> editComment.setContent(content));
        commentRepository.save(editComment);
        return editComment.getPost().getId();
    }
}
