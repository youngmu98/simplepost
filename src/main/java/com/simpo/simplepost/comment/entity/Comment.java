package com.simpo.simplepost.comment.entity;

import com.simpo.simplepost.global.common.BaseEntity;
import com.simpo.simplepost.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment(String content) {
        this.content = content;
    }

    public void addPost(Post post) {
        if (post != null) {
            this.post = post;
        }
    }

    public void updateContent(String content) {
        this.content = content;
    }

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;


}
