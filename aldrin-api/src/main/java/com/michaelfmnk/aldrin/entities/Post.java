package com.michaelfmnk.aldrin.entities;


import com.michaelfmnk.aldrin.dtos.PostDto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Length(max = 300, message = "title must not contain more then 300 characters")
    private String title;
    private LocalDateTime date;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "postId"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId")
    )
    private List<User> likes;

    public void merge(PostDto dto) {
        this.title = dto.getTitle();
    }
}
