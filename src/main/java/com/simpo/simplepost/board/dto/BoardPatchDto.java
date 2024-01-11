package com.simpo.simplepost.board.dto;

import com.simpo.simplepost.board.entity.Board;
import lombok.Data;

@Data
public class BoardPatchDto {
    private Long id;
    private String title;
    private String description;

    public Board toEntity(){
        return new Board(id, title, description);
    }
}
