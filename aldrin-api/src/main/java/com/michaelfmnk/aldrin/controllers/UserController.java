package com.michaelfmnk.aldrin.controllers;

import com.michaelfmnk.aldrin.dtos.UserDto;
import com.michaelfmnk.aldrin.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Api.ROOT_PATH)
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(Api.Users.USER_BY_USERNAME)
    public UserDto getUserByUsername(@PathVariable("username") String username){
        return userService.findUserByLogin(username);
    }
}
