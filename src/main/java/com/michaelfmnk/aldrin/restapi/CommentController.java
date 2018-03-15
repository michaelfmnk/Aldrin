package com.michaelfmnk.aldrin.restapi;


import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.postgres.CommentRepository;
import com.michaelfmnk.aldrin.postgres.dao.Comment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comments")
@Api(value = "api/comments/{id}")
public class CommentController {

    private CommentRepository commentRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @GetMapping("{id}")
    @ApiOperation(
            httpMethod = "GET",
            value = "returns comment with a specified id",
            response = Comment.class
    )
    public ResponseEntity<?> getCommentById(@PathVariable Long id){
        Comment comment = commentRepository.getCommentById(id);
        if (comment == null){
            throw new ResourceNotFoundException("Comment with id="+id+" not found");
        }
        return ResponseEntity.ok(comment);
    }


    @PutMapping("{id}")
    @ApiOperation(
            httpMethod = "PUT",
            value = "updates comment (only text for now)"
    )
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody @Valid Comment newComment){
        Comment originalComment = commentRepository.getCommentById(id);
        originalComment.setText(newComment.getText());
        commentRepository.save(originalComment);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("{id}")
    @ApiOperation(
            httpMethod = "DELETE",
            value = "deletes comment"
    )
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        Comment comment = commentRepository.getCommentById(id);
        if (comment == null) {
            throw new ResourceNotFoundException("Comment with id=" + id + " not found");
        }
        commentRepository.delete(comment);
        return ResponseEntity.ok("The delete was successful");
    }


}
