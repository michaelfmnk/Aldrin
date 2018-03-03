package com.michaelfmnk.aldrin.restapi;

import com.michaelfmnk.aldrin.postgres.PostRepository;
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
    @Autowired
    PostRepository postRepository;

    @GetMapping
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
