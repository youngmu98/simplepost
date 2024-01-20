package com.simpo.simplepost.post.entity;

import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public void addBoard(Board board) {
        if (board != null) {
            this.board = board;
            board.addPost(this);
        }
    }

}
