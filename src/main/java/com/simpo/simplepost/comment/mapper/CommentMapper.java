package com.simpo.simplepost.comment.mapper;

import com.simpo.simplepost.comment.dto.CommentCreateDto;
import com.simpo.simplepost.comment.dto.CommentPatchDto;
import com.simpo.simplepost.comment.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment commentCreateDtoToComment(CommentCreateDto commentCreateDto);

    Comment commentPatchDtoToComment(CommentPatchDto commentPatchDto);
}
