package com.michaelfmnk.aldrin.restapi;

import com.michaelfmnk.aldrin.postgres.PostRepository;
import com.michaelfmnk.aldrin.postgres.dao.Post;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
public class MainFeedController {

    private PostRepository postRepository;

    @Autowired
    public MainFeedController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping
    @ApiOperation(httpMethod = "GET",
            value = "finds posts of users that you're subscribed for",
            response = Post.class,
            responseContainer = "List"
    )
    public ResponseEntity<?> getFeed(@PageableDefault(direction = Sort.Direction.DESC, size = 50) Pageable pageable,
                                     Authentication authentication) {
        return ResponseEntity.ok(
                postRepository.findPostByAuthorFollowersUsername(
                        authentication.getName(),
                        pageable
                )
        );
    }
}
