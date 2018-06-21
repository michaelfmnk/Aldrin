package com.michaelfmnk.aldrin.controllers;


import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.repositories.CommentRepository;
import com.michaelfmnk.aldrin.repositories.PostRepository;
import com.michaelfmnk.aldrin.repositories.UserRepository;
import com.michaelfmnk.aldrin.entities.Comment;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(Api.ROOT_PATH)
public class PostController {


    private final PostRepository postRepository;
    //private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final PostService postService;


    @GetMapping(Api.Posts.POST_BY_ID)
    public PostDto getPostById(@PathVariable Integer id){
        return postService.findPostById(id);
    }


    @PutMapping(Api.Posts.POST_BY_ID)
    public PostDto updatePost(@PathVariable Integer id, @RequestBody PostDto postDto){
        return postService.updatePost(id, postDto);
    }


//    @PostMapping("")
//    public ResponseEntity<?> postPost(@RequestParam("file") MultipartFile file,
//                                      @RequestPart("title") String title,
//                                      Authentication authentication,
//                                      RedirectAttributes redirectAttributes){
//        Path path = storageService.store(file);
//        User user = userRepository.findUserByUsername(authentication.getName());
//        Photo photo = new Photo();
//        Post post = new Post();
//        photo.setUrl(path.toString()); //setting url to uploaded image
//        post.setTitle(title);
//        post.setAuthor(user);
//
//        post.setPhoto(photo);  //setting relations between post and photo
//        photo.setPost(post);
//        postRepository.save(post);
//
//        return ResponseEntity.ok(post);
//
//    }


    @GetMapping(Api.Posts.POST_COMMENTS)
    public List<CommentDto> getComments(@PathVariable Integer id, @ModelAttribute PageSortParams params){
        return postService.getCommentsForPost(id, params);
    }


    @PostMapping("{id}/comments")
    public ResponseEntity<?> postComment(@PathVariable Long id,
                                         @RequestBody @Valid Comment comment, Authentication authentication){
//        User user = userRepository.findUserByUsername(authentication.getName());
//        Post post = postRepository.findPostById(id);
//        if (post == null){
//            throw new ResourceNotFoundException("Post with commentId="+id+" not found");
//        }
//        comment.setPost(post);
//        comment.setUser(user);
//        commentRepository.save(comment);
     //  return ResponseEntity.ok(post);
        return null;
    }


    @PostMapping("{id}/likes")
    public ResponseEntity<?> setLike(@PathVariable Long id,
                                     Authentication authentication){
//        Post post = postRepository.findPostById(id);
//        User user = userRepository.findUserByUsername(authentication.getName());
//        if (post == null){
//            throw new ResourceNotFoundException("Post with commentId="+id+" not found");
//        }
//        if (post.hasLikeByUser(user.getUsername())){
//            return ResponseEntity.ok("Already liked");
//        }
//        post.getLikes().add(user);
//        postRepository.save(post);
//        return ResponseEntity.ok("Liked");
        return null;
    }


    @DeleteMapping("{id}/likes")
    public ResponseEntity<?> deleteLike(@PathVariable Long id,
                                        Authentication authentication){
//        Post post = postRepository.findPostById(id);
//        if (post == null){
//            throw new ResourceNotFoundException("Post with commentId="+id+" not found");
//        }
//        post.getLikes().removeIf((u) -> u.getUsername().equals(authentication.getName()));
//        postRepository.save(post);
//        return ResponseEntity.ok("Like removed");\
        return null;
    }
}
