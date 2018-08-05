package com.michaelfmnk.aldrin.controllers;

import com.michaelfmnk.aldrin.dtos.Pagination;
import com.michaelfmnk.aldrin.dtos.PostDto;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.security.UserAuthentication;
import com.michaelfmnk.aldrin.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Api.ROOT_PATH)
@AllArgsConstructor
public class MainFeedController {

    private final PostService postService;

    @GetMapping(Api.FEED)
    public Pagination<PostDto> getFeed(@ModelAttribute PageSortParams params, UserAuthentication auth) {
        return postService.getFeed(auth.getId(), params);
    }
}
