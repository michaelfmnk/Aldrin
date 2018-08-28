package com.michaelfmnk.aldrin.controllers;

import com.michaelfmnk.aldrin.dtos.*;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Api.ROOT_PATH)
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(Api.Users.USER_BY_USERNAME)
    public UserDto getUserByUsername(@PathVariable("username") String username){
        return userService.findUserByLogin(username);
    }

    @GetMapping(Api.Users.USER_POSTS)
    public Pagination<PostDto> getUserPosts(@PathVariable("user_id") Integer userId,
                                            @ModelAttribute PageSortParams params) {
        return userService.findUserPosts(userId, params);
    }

    @PostMapping(Api.Users.VERIFY)
    public TokenContainer verifyCode(@PathVariable("user_id") Integer userId,
                                     @RequestBody VerificationCodeContainer verificationCode) {
        return userService.verifyCode(userId, verificationCode);
    }
}
