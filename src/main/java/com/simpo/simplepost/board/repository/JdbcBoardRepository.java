package com.simpo.simplepost.board.repository;

import com.simpo.simplepost.board.entity.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBoardRepository implements JdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBoardRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Board> rowMapper = (rs, rowNum) -> {
        Board board = Board.builder()
                .id(rs.getLong("board_id"))
                .title(rs.getString("title"))
                .description(rs.getString("description"))
                .build();
        board.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        board.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

        return board;
    };

    @Override
    public List<Board> findAll() {
        return jdbcTemplate.query("SELECT * FROM board", rowMapper);
    }

    @Override
    public Optional<Board> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Board saveBoard(Board board) {
        return null;
    }

    @Override
    public void deleteBoard(Long id) {
    }
}
