package com.michaelfmnk.aldrin.restapi;


import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.postgres.CommentRepository;
import com.michaelfmnk.aldrin.postgres.PostRepository;
import com.michaelfmnk.aldrin.postgres.UserRepository;
import com.michaelfmnk.aldrin.postgres.dao.Comment;
import com.michaelfmnk.aldrin.postgres.dao.Post;
import com.michaelfmnk.aldrin.postgres.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/post/")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

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


    @PostMapping("{id}/comments")
    public ResponseEntity<?> postComment(@PathVariable Long id,
                                         @RequestBody @Valid Comment comment, Authentication authentication){
        User user = userRepository.findUserByUsername(authentication.getName());
        Post post = postRepository.findPostById(id);
        if (user == null){
            // throw exception
        }
        if (post == null){
            //throw exception
        }
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        return ResponseEntity.ok(post);

    }

    @PostMapping("{id}/likes")
    public ResponseEntity<?> setLike(@PathVariable Long id,
                                     Authentication authentication){
        Post post = postRepository.findPostById(id);
        User user = userRepository.findUserByUsername(authentication.getName());
        if (post == null){
            throw new ResourceNotFoundException("");
        }
        if (post.hasLikeByUser(user.getUsername())){
            return ResponseEntity.ok("Already liked");
        }
        post.getLikes().add(user);
        postRepository.save(post);
        return ResponseEntity.ok("Liked");
    }


    @DeleteMapping("{id}/likes")
    public ResponseEntity<?> deleteLike(@PathVariable Long id,
                                        Authentication authentication){
        Post post = postRepository.findPostById(id);
        if (post == null){
            throw new ResourceNotFoundException("Post with id="+id+" not found");
        }
        post.getLikes().removeIf((u) -> u.getUsername().equals(authentication.getName()));
        postRepository.save(post);
        return ResponseEntity.ok("Like removed");
    }
}
