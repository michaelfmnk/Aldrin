package com.michaelfmnk.aldrin.controllers;


import com.michaelfmnk.aldrin.dtos.AuthRequest;
import com.michaelfmnk.aldrin.dtos.TokenContainer;
import com.michaelfmnk.aldrin.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Api.ROOT_PATH)
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(Api.Auth.LOGIN)
    public TokenContainer createAuthenticationToken(@RequestBody AuthRequest request) {
        return authService.createToken(request);
    }

    @GetMapping(Api.Auth.LOGIN)
    public TokenContainer refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping(Api.Auth.SIGN_UP)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody AuthRequest request) {
        authService.signUp(request);
    }
}
