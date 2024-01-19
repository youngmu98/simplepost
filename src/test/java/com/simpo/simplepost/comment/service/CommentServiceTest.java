package com.simpo.simplepost.comment.service;

import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.comment.repository.CommentRepository;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;
    @Mock
    private PostRepository postRepository;


    @DisplayName("댓글 생성 테스트")
    @Test
    void createCommentTest(){
        // given
        Post post1 = new Post();

        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post1));

        Comment comment1 = new Comment();
        comment1.setContent("댓글1");
        comment1.setPost(post1);
        Comment comment2 = new Comment();
        comment2.setContent("댓글2");
        comment2.setPost(post1);

        List<Comment> mockComments = Arrays.asList(comment1, comment2);
        when(commentRepository.findAllByPostOrderByCreatedAtDesc(any(Post.class))).thenReturn(mockComments);

        // when
        commentService.createComment(comment1, 1L);
        List<Comment> comments = commentService.findCommentsByPost(post1);

        // then
        Assertions.assertThat(comments).hasSize(2);
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteCommentTest() {
        Post post1 = new Post();

        Comment comment1 = new Comment();
        comment1.setContent("댓글1");
        comment1.setPost(post1);

        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment1));

        // when
        commentService.deleteComment(1L);

        // then
        verify(commentRepository).delete(any(Comment.class));
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void editCommentTest() {
        Post post1 = new Post();

        Comment comment1 = new Comment();
        comment1.setContent("댓글1");
        comment1.setPost(post1);
        Comment editComment = new Comment();
        editComment.setContent("댓글2");

        when(commentRepository.findById(any(Long.class))).thenReturn(Optional.of(comment1));

        commentService.editComment(1L, editComment);
        comment1.setContent(editComment.getContent());

        Assertions.assertThat(comment1.getContent()).isEqualTo("댓글2");
    }
}