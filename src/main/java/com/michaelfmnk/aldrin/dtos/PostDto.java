package com.michaelfmnk.aldrin.dtos;

import com.michaelfmnk.aldrin.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto implements Serializable {
    private Integer postId;
    private String title;
    private LocalDateTime date;
    private UserDto author;
    private List<Comment> comments;
    private Integer likes;
}
