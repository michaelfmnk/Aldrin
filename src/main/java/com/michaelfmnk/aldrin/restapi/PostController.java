package com.michaelfmnk.aldrin.restapi;


import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.postgres.CommentRepository;
import com.michaelfmnk.aldrin.postgres.PostRepository;
import com.michaelfmnk.aldrin.postgres.dao.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post/")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("{id}/")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        Post post = postRepository.findPostById(id);
        if (post==null){
            throw new ResourceNotFoundException("Post with id " + id +" not found");
        }
        return ResponseEntity.ok(post);
    }


    @GetMapping("{id}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long id,
                                         @PageableDefault(
                                                 size = 100,
                                                 sort = "date"
                                         ) Pageable pageable){
        return ResponseEntity.ok(
                commentRepository.getCommentsByPostId(id, pageable)
        );

    }
}
