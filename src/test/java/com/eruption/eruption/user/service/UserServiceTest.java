package com.eruption.eruption.user.service;

import com.eruption.eruption.global.exception.DuplicateEmailException;
import com.eruption.eruption.user.dto.SignUpRequest;
import com.eruption.eruption.user.dto.SignUpResponse;
import com.eruption.eruption.user.entity.User;
import com.eruption.eruption.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks UserService userService;

    @Test
    void 정상_가입시_응답에_이름과_이메일(){
        // given
        SignUpRequest request = new SignUpRequest("a@a.com", "password123", "은섭");
        given(userRepository.existsByEmail("a@a.com")).willReturn(false);
        given(passwordEncoder.encode("password123")).willReturn("encodedPw");
        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        SignUpResponse response = userService.signUp(request);
        // then
        assertThat(response.email()).isEqualTo("a@a.com");
        assertThat(response.name()).isEqualTo("은섭");
    }
    @Test
    void 이미_있는_이메일이면_예외가_난다() {
        // given
        given(userRepository.existsByEmail("a@a.com")).willReturn(true);

        // when & then
        assertThatThrownBy(() ->
                userService.signUp(new SignUpRequest("a@a.com", "password123", "은섭")))
                .isInstanceOf(DuplicateEmailException.class);
    }
    @Test
    void 비밀번호가_암호화돼서_저장된다(){
        // given
        SignUpRequest request = new SignUpRequest("a@a.com", "password123", "은섭");

        given(userRepository.existsByEmail(request.email())).willReturn(false);
        given(passwordEncoder.encode("password123")).willReturn("encodedPw");
        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));   // ← 추가

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        userService.signUp(request);

        verify(userRepository).save(captor.capture());      // save에 넘어간 User를 붙잡음
        assertThat(captor.getValue().getPassword()).isEqualTo("encodedPw");

    }

}
