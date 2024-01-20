package com.simpo.simplepost.comment.repository;

import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPostOrderByCreatedAtDesc(Post post);

    void deleteByPostId(Long postId);
}
