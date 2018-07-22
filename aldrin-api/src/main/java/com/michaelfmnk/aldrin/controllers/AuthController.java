package com.michaelfmnk.aldrin.controllers;


import com.michaelfmnk.aldrin.dtos.AuthRequest;
import com.michaelfmnk.aldrin.dtos.TokenContainer;
import com.michaelfmnk.aldrin.services.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Api.ROOT_PATH)
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "Login user", notes = "can access anyone")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User logged in", response = TokenContainer.class),
            @ApiResponse(code = 401, message = "Username or password is incorect")
    })
    @PostMapping(Api.Auth.LOGIN)
    public TokenContainer createAuthenticationToken(@ApiParam(value = "Required: email and password")
                                                        @RequestBody AuthRequest request) {
        return authService.createToken(request);
    }

    @GetMapping(Api.Auth.LOGIN)
    public TokenContainer refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping(Api.Auth.SIGN_UP)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Validated AuthRequest request) {
        authService.signUp(request);
    }
}
