package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import com.michaelfmnk.aldrin.services.converters.CommentConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class CommentService {
//  /  private final CommentRepository commentRepository;
//    private final CommentConverter commentConverter;
//
//    public void deleteCommentById(Integer id) {
//        if(!commentRepository.existsById(id)) {
//            throw new EntityNotFoundException(format("no comment was found with commentId=%s", id));
//        }
      //  commentRepository.delete(id);
    //}

//    public CommentDto getCommentById(Integer id) {
//        Comment comment = commentRepository.findCommentById(id)
//                .orElseThrow(() -> new EntityNotFoundException(format("no comment was found with commentId=%s", id)));
//        return commentConverter.toDto(comment);
//    }
}
