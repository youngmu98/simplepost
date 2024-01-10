package com.simpo.simplepost.board.service;

import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.repository.JdbcBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private final JdbcBoardRepository boardRepository;

    public BoardService(JdbcBoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
