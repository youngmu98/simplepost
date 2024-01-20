package com.simpo.simplepost.post.service;

import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.repository.JdbcBoardRepository;
import com.simpo.simplepost.comment.repository.CommentRepository;
import com.simpo.simplepost.global.exception.ExceptionCode;
import com.simpo.simplepost.global.exception.ServiceLogicException;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final JdbcBoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, JdbcBoardRepository boardRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
    }

    public Page<Post> findPostsByBoardAndKeyword(Board board, String keyword, PageRequest pageRequest) {
        if (keyword != null && !keyword.isEmpty()) {
            return postRepository.findAllByBoardAndTitleContaining(board, keyword, pageRequest);
        } else {
            return postRepository.findAllByBoardOrderByCreatedAtDesc(board, pageRequest);
        }
    }

    public void addPost(Post post, Long boardId) {
        post.addBoard(boardRepository.findById(boardId).orElseThrow(() -> new ServiceLogicException(ExceptionCode.BOARD_NOT_FOUND)));
        postRepository.save(post);
    }

    public Post findByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ServiceLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    public Post updatePost(Post editpost, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ServiceLogicException(ExceptionCode.POST_NOT_FOUND));

        Optional.ofNullable(editpost.getTitle()).ifPresent(title -> post.setTitle(title));
        Optional.ofNullable(editpost.getContent()).ifPresent(content -> post.setContent(content));

        postRepository.save(post);
        return post;
    }

    @Transactional
    public Long deletePostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ServiceLogicException(ExceptionCode.POST_NOT_FOUND));
        Long boardId = post.getBoard().getId();
        commentRepository.deleteByPostId(postId);
        postRepository.delete(post);
        return boardId;
    }
}
