package com.michaelfmnk.aldrin.security;


import com.michaelfmnk.aldrin.entities.Authority;
import com.michaelfmnk.aldrin.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {
    private JwtUserFactory(){}

    public static JwtUser create(User user){
        return new JwtUser(
                user.getUserId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getAuthorities()),
                true,
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
    }
}
