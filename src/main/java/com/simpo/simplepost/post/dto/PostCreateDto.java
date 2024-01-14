package com.simpo.simplepost.post.dto;

import com.simpo.simplepost.post.entity.Post;
import lombok.Data;

@Data
public class PostCreateDto {
    private Long boardId;
    private String title;
    private String content;

    public Post toEntity() {
        return new Post(title, content);
    }
}
