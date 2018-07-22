package com.michaelfmnk.aldrin.controllers;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.security.UserAuthentication;
import com.michaelfmnk.aldrin.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(Api.ROOT_PATH)
public class CommentController {
    private final CommentService commentService;

    @PutMapping(Api.Comments.COMMENT_BY_ID)
    @PreAuthorize("hasPermission(#commentId, 'OWN_COMMENT', 'USER')")
    public CommentDto updateComment(@PathVariable("comment_id") Integer commentId,
                                    @RequestBody CommentDto commentDto,
                                    UserAuthentication auth) {
        commentDto = commentDto.toBuilder()
                .id(commentId)
                .userId(auth.getId())
                .build();
        return commentService.updateComment(commentId, commentDto);
    }
}
