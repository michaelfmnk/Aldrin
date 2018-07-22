package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import com.michaelfmnk.aldrin.utils.ConverterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ConverterService converterService;

    public Comment getValidCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("no comment was found with id=%s", id)));
    }

    public CommentDto updateComment(Integer commentId, CommentDto commentDto) {
        Comment comment = getValidCommentById(commentId);
        comment.update(commentDto);
        comment = commentRepository.save(comment);
        return converterService.toDto(comment);
    }
}
