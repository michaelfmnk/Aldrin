package com.michaelfmnk.aldrin.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto implements Serializable {
    private Integer commentId;
    private String content;
    private LocalDateTime date;
    private Integer postId;
    private Integer userId;
}
