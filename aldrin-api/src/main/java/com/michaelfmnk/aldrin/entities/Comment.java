package com.michaelfmnk.aldrin.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    private String content;

    private LocalDateTime date;

    @Column(name = "replied_comment_id")
    private Integer repliedCommentId;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "replied_comment_id", updatable = false, insertable = false)
    private Comment repliedComment;

    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false, insertable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    public Optional<Comment> getRepliedComment() {
        return Optional.ofNullable(this.repliedComment);
    }
}
