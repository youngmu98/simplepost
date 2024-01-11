package com.simpo.simplepost.board.repository;

import com.simpo.simplepost.board.entity.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBoardRepository implements JdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBoardRepository(JdbcTemplate jdbcTemplate) {
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
        if (board.getId() == null) {
            LocalDateTime currentTime = LocalDateTime.now();
            board.setCreatedAt(currentTime);
            board.setUpdatedAt(currentTime);

            jdbcTemplate.update("INSERT INTO board(title, description, created_at, updated_at) VALUES (?,?,?,?)",
                    board.getTitle(), board.getDescription(), Timestamp.valueOf(board.getCreatedAt()), Timestamp.valueOf(board.getUpdatedAt()));
        } else {
            // 수동으로 수정시간 주입
            board.setUpdatedAt(LocalDateTime.now());
            jdbcTemplate.update("UPDATE board SET title = ?, description = ?, updated_at=? WHERE board_id = ?",
                    board.getTitle(),
                    board.getDescription(),
                    Timestamp.valueOf(board.getUpdatedAt()),
                    board.getId()
            );
        }
        return board;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM board WHERE board_id = ?",
                rowMapper,
                id)
                .stream().findFirst();
    }

    @Override
    public void deleteBoard(Long id) {
        jdbcTemplate.update("DELETE FROM board WHERE board_id = ?", id);
    }
}
