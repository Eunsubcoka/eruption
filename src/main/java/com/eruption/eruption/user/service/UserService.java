package com.eruption.eruption.user.service;

import com.eruption.eruption.global.exception.DuplicateEmailException;
import com.eruption.eruption.user.dto.SignUpRequest;
import com.eruption.eruption.user.dto.SignUpResponse;
import com.eruption.eruption.user.entity.User;
import com.eruption.eruption.user.enums.UserRole;
import com.eruption.eruption.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.email())){
            throw new DuplicateEmailException();
        }
        User user = User.builder()
                .email(signUpRequest.email())
                .name(signUpRequest.name())
                .password(passwordEncoder.encode(signUpRequest.password()))  // 비밀번호 암호화
                .role(UserRole.ROLE_USER)
                .build();

        return SignUpResponse.from(userRepository.save(user));
    }
}
