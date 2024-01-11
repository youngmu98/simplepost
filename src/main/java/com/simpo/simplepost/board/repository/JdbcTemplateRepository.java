package com.simpo.simplepost.board.repository;

import com.simpo.simplepost.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface JdbcTemplateRepository {

    List<Board> findAll();

    Optional<Board> findById(Long id);

    Board saveBoard(Board board);

    void deleteBoard(Long id);
}
