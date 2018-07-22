package com.michaelfmnk.aldrin.entities;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.utils.SortingInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comments")
public class Comment implements Updatable<CommentDto> {

    public static final String DATE = "date";
    public static final String DEFAULT_SORT = DATE;
    public static SortingInfo SORTING_INFO = new SortingInfo(DEFAULT_SORT);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    private String content;

    @CreationTimestamp
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

    @Override
    public void update(CommentDto dto) {
        this.content = dto.getContent();
    }
}
