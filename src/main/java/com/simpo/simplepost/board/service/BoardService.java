package com.simpo.simplepost.board.service;

import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.repository.JdbcBoardRepository;
import com.simpo.simplepost.global.exception.ExceptionCode;
import com.simpo.simplepost.global.exception.ServiceLogicException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final JdbcBoardRepository boardRepository;

    public BoardService(JdbcBoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public void createBoard(Board requestBoard) {
        boardRepository.saveBoard(requestBoard);
    }

    public void updateBoard(Board board){
        Board editBoard = boardRepository.findById(board.getId())
                .orElseThrow(() -> new ServiceLogicException(ExceptionCode.BOARD_NOT_FOUND));

        Optional.ofNullable(board.getTitle())
                .ifPresent(title -> editBoard.setTitle(title));
        Optional.ofNullable(board.getDescription())
                .ifPresent(description -> editBoard.setDescription(description));

        boardRepository.saveBoard(editBoard);
    }

    public Board findById(Long boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ServiceLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteBoard(boardId);
    }


    public boolean findByTitle(String title) {
        return boardRepository.findByTitle(title);
    }
}
