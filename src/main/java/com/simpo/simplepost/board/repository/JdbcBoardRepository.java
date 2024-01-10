package com.simpo.simplepost.board.repository;

import com.simpo.simplepost.board.entity.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
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
    public Board saveBoard(Board board) {
        if (board.getId() == null){
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update((con) -> {
                PreparedStatement ps =
                        con.prepareStatement(
                                "INSERT INTO board(title, description, user_id, created_at, updated_at) VALUES (?,?,?,?,?)",
                                new String[]{"board_id"});
                ps.setString(1, board.getTitle());
                ps.setString(2, board.getDescription());
                ps.setLong(3, board.getUser().getId());
                ps.setTimestamp(4, Timestamp.valueOf(board.getCreatedAt()));
                ps.setTimestamp(5, Timestamp.valueOf(board.getUpdatedAt()));
                return ps;
            },keyHolder);
            Number key = keyHolder.getKey();
            if (key != null) {
                board.setId(key.longValue());
            }
        }else {
            jdbcTemplate.update("UPDATE board SET title = ?, description = ?, user_id = ?, updated_at=? WHERE board_id = ?",
                    board.getTitle(),
                    board.getDescription(),
                    board.getUser().getId(),
                    Timestamp.valueOf(board.getUpdatedAt()),
                    board.getId()
            );
        }
        return board;
    }

    @Override
    public Optional<Board> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public void deleteBoard(Long id) {
    }
}
