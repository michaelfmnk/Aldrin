package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ConverterService converterService;

    public void deleteCommentById(Integer id) {
        if(!commentRepository.existsById(id)) {
            throw new EntityNotFoundException(format("no comment was found with id=%s", id));
        }
        commentRepository.deleteById(id);
    }

    public CommentDto getCommentById(Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("no comment was found with id=%s", id)));
        return converterService.toDto(comment);
    }
}
