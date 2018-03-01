package com.michaelfmnk.aldrin.security.repository;


import com.michaelfmnk.aldrin.postgres.UserRepository;
import com.michaelfmnk.aldrin.postgres.dao.User;
import com.michaelfmnk.aldrin.security.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(s);
        if(user == null){
            throw new UsernameNotFoundException(s);
        }else{
            return JwtUserFactory.create(user);
        }
    }
}
