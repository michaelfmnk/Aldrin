package com.michaelfmnk.aldrin.restapi;


import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.postgres.CommentRepository;
import com.michaelfmnk.aldrin.postgres.dao.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id){
        Comment comment = commentRepository.getCommentById(id);
        if (comment == null){
            throw new ResourceNotFoundException("Comment with id="+id+" not found");
        }
        return ResponseEntity.ok(comment);
    }


    @PutMapping("{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody @Valid Comment newComment){
        Comment originalComment = commentRepository.getCommentById(id);
        originalComment.setText(newComment.getText());
        commentRepository.save(originalComment);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("{id}")
    public  ResponseEntity<?> deleteComment(@PathVariable Long id){
        Comment comment = commentRepository.getCommentById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id=" + id + " not found");
        }
        commentRepository.delete(comment);
        return ResponseEntity.ok("The delete was successful");
    }


}
