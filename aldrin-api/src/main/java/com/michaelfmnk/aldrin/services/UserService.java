package com.michaelfmnk.aldrin.services;


import com.michaelfmnk.aldrin.dtos.UserDto;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.repositories.UserRepository;
import com.michaelfmnk.aldrin.security.JwtUserFactory;
import com.michaelfmnk.aldrin.utils.ConverterService;
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
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(login)
                .map(JwtUserFactory::create)
                .orElseThrow(() -> new EntityNotFoundException(format("no user was found with username=%s", login)));
    }

    public UserDto findUserByLogin(String login) {
        return converterService.toDto(findValidUserByLogin(login));
    }

    public User findValidUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format("no user found with id=%s", id)));
    }


    public User findValidUserByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(format("no user found with login=%s", login)));
    }
}
