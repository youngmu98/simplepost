package com.simpo.simplepost.post.mapper;

import com.simpo.simplepost.post.dto.PostCreateDto;
import com.simpo.simplepost.post.dto.PostPatchDto;
import com.simpo.simplepost.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post PostCreateDtoToPost(PostCreateDto postCreateDto);

    Post PostPatchDtoToPost(PostPatchDto postPatchDto);

}
