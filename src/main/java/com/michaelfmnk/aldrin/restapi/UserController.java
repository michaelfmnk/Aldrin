package com.michaelfmnk.aldrin.restapi;

import com.michaelfmnk.aldrin.exception.ResourceNotFoundException;
import com.michaelfmnk.aldrin.postgres.UserRepository;
import com.michaelfmnk.aldrin.postgres.dao.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserRepository userRepository;


    @GetMapping("/{username}")
    @ApiOperation(
            httpMethod = "GET",
            value = "Returns user information",
            response = User.class,
            produces = "application/json"
    )
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username){
        User user = userRepository.findUserByUsername(username);
        if (user==null){
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }
        return ResponseEntity.ok(userRepository.findUserByUsername(username));
    }
}
