package com.michaelfmnk.aldrin.controllers;


import com.michaelfmnk.aldrin.dtos.CommentDto;
import com.michaelfmnk.aldrin.dtos.Pagination;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.security.JwtUser;
import com.michaelfmnk.aldrin.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(Api.ROOT_PATH)
public class PostController {

    private final PostService postService;

    @GetMapping(Api.Posts.POST_BY_ID)
    public PostDto getPostById(@PathVariable("post_id") Integer id){
        return postService.findPostById(id);
    }

    @PutMapping(Api.Posts.POST_BY_ID)
    public PostDto updatePost(@PathVariable("post_id") Integer id, @RequestBody PostDto postDto){
        return postService.updatePost(id, postDto);
    }

    @PostMapping(Api.Posts.POST_LIKES)
    @ResponseStatus(HttpStatus.CREATED)
    public void setLike(@PathVariable("post_id") Integer id, Authentication auth){
        postService.addLikeForPost(id, getUserIdFromAuthentication(auth));
    }

    @DeleteMapping(Api.Posts.POST_LIKES)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLike(@PathVariable("post_id") Integer id, Authentication auth){
        postService.deleteLikeForPost(id, getUserIdFromAuthentication(auth));
    }

    @GetMapping(Api.Posts.COMMENTS)
    public Pagination<CommentDto> getComments(@PathVariable("post_id") Integer postId, @ModelAttribute PageSortParams params){
        return postService.getCommentsForPost(postId, params);
    }

    @PostMapping(Api.Posts.COMMENTS)
    public PostDto postComment(@PathVariable("post_id") Integer postId, @RequestBody @Valid CommentDto commentDto,
                               Authentication auth){
        Integer userId = getUserIdFromAuthentication(auth);
        commentDto = commentDto.toBuilder()
                .userId(userId)
                .postId(postId)
                .build();
        return postService.addCommentToPost(postId,  userId, commentDto);
    }




    private Integer getUserIdFromAuthentication(Authentication auth) {
        return ((JwtUser) auth.getPrincipal()).getId();
    }
}
