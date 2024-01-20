package com.simpo.simplepost.comment.dto;

import com.simpo.simplepost.comment.entity.Comment;
import lombok.Data;

@Data
public class CommentPatchDto {
    private String content;
}
