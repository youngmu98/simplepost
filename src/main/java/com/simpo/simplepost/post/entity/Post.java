package com.simpo.simplepost.post.entity;

import com.simpo.simplepost.common.BaseEntity;
import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post(Long postId, String title, String content) {
        this.id = postId;
        this.title = title;
        this.content = content;
    }

    public void addBoard(Board board) {
        if (board != null) {
            this.board = board;
            board.addPost(this);
        }
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
