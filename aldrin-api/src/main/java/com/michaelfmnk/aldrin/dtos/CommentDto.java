package com.michaelfmnk.aldrin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer id;
    @NotEmpty
    private String content;
    private LocalDateTime date;
    private Integer postId;
    private Integer userId;
    private Integer repliedCommentId;
}
