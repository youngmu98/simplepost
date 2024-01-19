package com.simpo.simplepost.comment.entity;

import com.simpo.simplepost.global.common.BaseEntity;
import com.simpo.simplepost.post.entity.Post;
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
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    //    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    public void addPost(Post post) {
        if (post != null) {
            this.post = post;
        }
    }

}
