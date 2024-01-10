package com.simpo.simplepost.board.entity;

import com.simpo.simplepost.board.dto.BoardResponseDto;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Post> posts;

    public Board() {}

    public Board(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public BoardResponseDto toBoardResponseDto(){
        return new BoardResponseDto(id, title, description, getCreatedAt(), getUpdatedAt());
    }
}
