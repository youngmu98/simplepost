package com.simpo.simplepost.post.service;

import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.repository.JdbcBoardRepository;
import com.simpo.simplepost.post.dto.PostCreateDto;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Page<Post> findPostsByBoardAndKeyword(Board board, String keyword, PageRequest pageRequest){
        if (keyword != null && !keyword.isEmpty()){
            return postRepository.findAllByBoardAndTitleContaining(board, keyword, pageRequest);
        }else{
            return postRepository.findAllByBoardOrderByCreatedAtDesc(board, pageRequest);
        }
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

    public Long deletePostById(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        Long boardId = post.getBoard().getId();
        postRepository.delete(post);
        return boardId;
    }

    public Post findByPostId(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post updatePost(Post editpost) {
        Post post = postRepository.findById(editpost.getId()).orElse(null);

        if (!post.getTitle().isEmpty()){
            post.setTitle(editpost.getTitle());
        }
        if (!post.getContent().isEmpty()) {
            post.setContent(editpost.getContent());
        }
        postRepository.save(post);
        return post;
    }
}
