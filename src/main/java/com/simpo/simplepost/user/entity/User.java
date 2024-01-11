package com.simpo.simplepost.user.entity;

import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.comment.entity.Comment;
import com.simpo.simplepost.post.entity.Post;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    private String userName;
    private String nickname;
    private String loginId;
    private String password;

//    @OneToMany(mappedBy = "user")
//    private List<Board> boards;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

}
