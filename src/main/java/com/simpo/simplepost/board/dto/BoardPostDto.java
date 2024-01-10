package com.simpo.simplepost.board.dto;

import com.simpo.simplepost.board.entity.Board;
import lombok.Data;

@Data
public class BoardPostDto {
    private String title;
    private String description;

    public Board toEntity(){
        return new Board(title, description);
    }

}
