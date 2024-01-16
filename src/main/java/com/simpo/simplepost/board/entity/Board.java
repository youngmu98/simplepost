package com.simpo.simplepost.board.entity;

import com.simpo.simplepost.global.common.BaseEntity;
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

    @Column(nullable = false, unique = true, length = 15)
    private String title;
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public Board() { }

    public void addPost(Post post) {
        if (post != null) {
            if (posts == null) {
                posts = new ArrayList<>();
            }
            posts.add(post);
        }
    }
}
