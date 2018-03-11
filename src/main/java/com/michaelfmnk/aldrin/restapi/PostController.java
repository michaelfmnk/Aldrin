package com.michaelfmnk.aldrin.restapi;


import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.postgres.CommentRepository;
import com.michaelfmnk.aldrin.postgres.PostRepository;
import com.michaelfmnk.aldrin.postgres.UserRepository;
import com.michaelfmnk.aldrin.postgres.dao.Comment;
import com.michaelfmnk.aldrin.postgres.dao.Photo;
import com.michaelfmnk.aldrin.postgres.dao.Post;
import com.michaelfmnk.aldrin.postgres.dao.User;
import com.michaelfmnk.aldrin.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    private final StorageService storageService;

    @Autowired
    public PostController(StorageService storageService){
        this.storageService = storageService;
    }

    @GetMapping("{id}/")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        Post post = postRepository.findPostById(id);
        if (post==null){
            throw new ResourceNotFoundException("Post with id " + id +" not found");
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping("")
    public ResponseEntity<?> postPost(@RequestParam("file") MultipartFile file,
                                      @RequestPart("title") String title,
                                      Authentication authentication,
                                      RedirectAttributes redirectAttributes){
        Path path = storageService.store(file);
        User user = userRepository.findUserByUsername(authentication.getName());
        Photo photo = new Photo();
        Post post = new Post();
        photo.setUrl(path.toString()); //setting url to uploaded image
        post.setTitle(title);
        post.setAuthor(user);

        post.setPhoto(photo);  //setting relations between post and photo
        photo.setPost(post);
        postRepository.save(post);

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
        if (post == null){
            throw new ResourceNotFoundException("Post with id="+id+" not found");
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
            throw new ResourceNotFoundException("Post with id="+id+" not found");
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
