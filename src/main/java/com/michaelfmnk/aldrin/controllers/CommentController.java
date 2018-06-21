package com.michaelfmnk.aldrin.controllers;


import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.services.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
@Api(value = "api/comments/{id}")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

//
//    @GetMapping("{id}")
//    public CommentDto getCommentById(@PathVariable Integer id){
//        return commentService.getCommentById(id);
//    }
//

    @PutMapping("{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody @Valid Comment newComment){
//        Comment originalComment = commentRepository.getCommentById(id);
//        originalComment.setText(newComment.getText());
//        commentRepository.save(originalComment);
        return ResponseEntity.ok("Updated");
    }

//    @DeleteMapping("{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteComment(@PathVariable Integer id){
//        commentService.deleteCommentById(id);
//    }


}
