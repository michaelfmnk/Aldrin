package com.michaelfmnk.aldrin.services;


import com.michaelfmnk.aldrin.dtos.UserDto;
import com.michaelfmnk.aldrin.repositories.UserRepository;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.security.JwtUserFactory;
import com.michaelfmnk.aldrin.services.converters.UserConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s)
                .orElseThrow(() -> new EntityNotFoundException(format("no user was found with commentId=%s", s)));
        return JwtUserFactory.create(user);
    }

    public UserDto findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(format("no user found with commentId=%s", username)));
        return userConverter.toDto(user);
    }




}
