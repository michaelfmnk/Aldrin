package com.michaelfmnk.aldrin.controllers;

import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
@AllArgsConstructor
public class MainFeedController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getFeed(@ModelAttribute PageSortParams params, Authentication authentication) {
        return postService.getFeed(authentication.getName(), params);
    }
}
