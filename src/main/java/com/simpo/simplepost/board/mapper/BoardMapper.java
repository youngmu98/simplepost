package com.simpo.simplepost.board.mapper;

import com.simpo.simplepost.board.dto.BoardPatchDto;
import com.simpo.simplepost.board.dto.BoardPostDto;
import com.simpo.simplepost.board.entity.Board;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostDtotoBoard(BoardPostDto boardPostDto);

    Board boardPatchDtoToboard(BoardPatchDto boardPatchDto);

}
