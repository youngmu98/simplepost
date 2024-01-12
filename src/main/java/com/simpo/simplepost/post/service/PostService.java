package com.simpo.simplepost.post.service;

import com.simpo.simplepost.board.repository.JdbcBoardRepository;
import com.simpo.simplepost.post.dto.PostCreateDto;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final JdbcBoardRepository boardRepository;

    public PostService(PostRepository postRepository, JdbcBoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
    }

    public void addPost(PostCreateDto postCreateDto) {
        Post post = postCreateDto.toEntity();
        post.addBoard(boardRepository.findById(postCreateDto.getBoardId()).orElse(null));
        postRepository.save(post);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public List<Post> findByBoardId(Long boardId) {
        List<Post> posts = postRepository.findByBoardId(boardId);
        return posts;
    }
}
