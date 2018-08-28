package com.michaelfmnk.aldrin.services;


import com.michaelfmnk.aldrin.dtos.*;
import com.michaelfmnk.aldrin.dtos.params.PageSortParams;
import com.michaelfmnk.aldrin.dtos.params.PageSortRequest;
import com.michaelfmnk.aldrin.entities.Post;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.entities.VerificationCode;
import com.michaelfmnk.aldrin.exceptions.BadRequestException;
import com.michaelfmnk.aldrin.repositories.PostRepository;
import com.michaelfmnk.aldrin.repositories.UserRepository;
import com.michaelfmnk.aldrin.repositories.VerificationCodeRepository;
import com.michaelfmnk.aldrin.security.JwtTokenUtil;
import com.michaelfmnk.aldrin.security.JwtUser;
import com.michaelfmnk.aldrin.security.JwtUserFactory;
import com.michaelfmnk.aldrin.utils.ConverterService;
import com.michaelfmnk.aldrin.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ConverterService converterService;
    private final MessagesService messagesService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(login)
                .map(JwtUserFactory::create)
                .orElseThrow(() -> new EntityNotFoundException(format("no user was found with username=%s", login)));
    }

    public UserDto findUserByLogin(String login) {
        return converterService.toDto(findValidUserByLogin(login));
    }

    public User findValidUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(format("no user found with id=%s", userId)));
    }

    public User findValidUserByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException(format("no user found with login=%s", login)));
    }

    public Pagination<PostDto> findUserPosts(Integer userId, PageSortParams params) {
        failIfUserNotValid(userId);
        Pageable pageable = PageSortRequest
                .builder()
                .limit(params.getLimit())
                .offset(params.getOffset())
                .sort(Post.SORTING_INFO.getDefaultSort(params.isAsc()))
                .build();

        Page<Post> postPage = postRepository.findPostByAuthor_UserId(userId, pageable);
        List<PostDto> postDtos = postPage.getContent()
                .stream()
                .map(converterService::toDto)
                .collect(Collectors.toList());

        return new Pagination<>(postDtos, postPage.getTotalElements());
    }

    private void failIfUserNotValid(Integer userId) {
        if (userRepository.existsById(userId)) {
            return;
        }
        throw new EntityNotFoundException(format("no user found with id=%s", userId));
    }

    @Transactional
    public TokenContainer verifyCode(Integer userId, VerificationCodeContainer verificationCode) {
        User user = findValidUserById(userId);
        if (user.isEnabled()) {
            throw new BadRequestException(messagesService.getMessage("user.activated.already"));
        }

        VerificationCode code = verificationCodeRepository
                .findById(VerificationCode.VerificationCodePK.builder()
                        .userId(userId)
                        .verificationCode(verificationCode.getCode())
                        .build())
                .orElseThrow(() -> new BadRequestException(messagesService.getMessage("code.not.valid")));

        user.setEnabled(true);
        userRepository.save(user);

        verificationCodeRepository.delete(code);

        final JwtUser jwtUser = JwtUserFactory.create(user);
        final String token = jwtTokenUtil.generateToken(jwtUser);
        return new TokenContainer(token);
    }
}
