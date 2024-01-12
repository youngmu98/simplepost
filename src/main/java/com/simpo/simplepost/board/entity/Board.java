package com.simpo.simplepost.board.entity;

import com.simpo.simplepost.common.BaseEntity;
import com.simpo.simplepost.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public Board() {

    }
    public Board(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Board(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
