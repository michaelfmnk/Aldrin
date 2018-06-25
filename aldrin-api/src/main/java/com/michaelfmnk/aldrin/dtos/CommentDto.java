package com.michaelfmnk.aldrin.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class CommentDto implements Serializable {
    private Integer commentId;
    private String content;
    private LocalDateTime date;
    private Integer postId;
    private Integer userId;
}
