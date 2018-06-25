package com.michaelfmnk.aldrin.services;


import com.michaelfmnk.aldrin.dtos.UserDto;
import com.michaelfmnk.aldrin.repositories.UserRepository;
import com.michaelfmnk.aldrin.security.JwtUserFactory;
import lombok.AllArgsConstructor;
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
    private final ConverterService converterService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(s)
                .map(JwtUserFactory::create)
                .orElseThrow(() -> new EntityNotFoundException(format("no user was found with commentId=%s", s)));

    }

    public UserDto findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .map(converterService::toDto)
                .orElseThrow(() -> new EntityNotFoundException(format("no user found with commentId=%s", username)));
    }

}
