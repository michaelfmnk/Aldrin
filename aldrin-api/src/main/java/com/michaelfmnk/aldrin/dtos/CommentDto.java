package com.michaelfmnk.aldrin.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
public class CommentDto {
    private Integer id;
    private String content;
    private LocalDateTime date;
    private Integer postId;
    private Integer userId;
    private Integer repliedCommentId;
}
