package com.michaelfmnk.aldrin.services;

import com.michaelfmnk.aldrin.dtos.AuthRequest;
import com.michaelfmnk.aldrin.dtos.TokenContainer;
import com.michaelfmnk.aldrin.entities.User;
import com.michaelfmnk.aldrin.entities.VerificationCode;
import com.michaelfmnk.aldrin.entities.VerificationCode.VerificationCodePK;
import com.michaelfmnk.aldrin.exceptions.BadRequestException;
import com.michaelfmnk.aldrin.props.AuthProperties;
import com.michaelfmnk.aldrin.repositories.UserRepository;
import com.michaelfmnk.aldrin.repositories.VerificationCodeRepository;
import com.michaelfmnk.aldrin.security.JwtTokenUtil;
import com.michaelfmnk.aldrin.security.JwtUser;
import com.michaelfmnk.aldrin.security.JwtUserFactory;
import com.michaelfmnk.aldrin.utils.CodeGenerator;
import com.michaelfmnk.aldrin.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

    private static final Integer CODE_LEN = 7;

    private final AuthProperties authProperties;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final MessagesService messagesService;
    private final UserService userService;
    private final MailjetService mailjetService;
    private final VerificationCodeRepository verificationCodeRepository;


    @Transactional
    public TokenContainer createToken(AuthRequest request) {
        User user = userService.findValidUserByLogin(request.getLogin());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final JwtUser jwtUser = JwtUserFactory.create(user);
        final String token = jwtTokenUtil.generateToken(jwtUser);
        return new TokenContainer(token);
    }

    public void signUp(AuthRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new BadRequestException(messagesService.getMessage("user.already.exists"));
        }
        User user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .build();

        user = userRepository.save(user);

        String code = CodeGenerator.generateCode();
        VerificationCode verificationCode = new VerificationCode(new VerificationCodePK(user.getUserId(), code));
        verificationCodeRepository.save(verificationCode);

        mailjetService.sendEmail(
                request.getLogin(),
                messagesService.getMessage("mailjet.signup.subject"),
                code);
    }

    public TokenContainer refreshToken(HttpServletRequest request) {
        String token = request.getHeader(authProperties.getHeaderName());
        String login = jwtTokenUtil.getLoginFromToken(token);
        User user = userService.findValidUserByLogin(login);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return new TokenContainer(refreshedToken);
        }

        throw new BadRequestException(messagesService.getMessage("auth.token.not.refreshable"));
    }
}
