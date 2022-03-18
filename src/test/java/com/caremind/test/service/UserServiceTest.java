package com.caremind.test.service;

import com.caremind.test.dto.TokenDto;
import com.caremind.test.dto.UserDto;

import com.caremind.test.exception.DuplicatedIdException;
import com.caremind.test.repository.UserRepository;
import com.caremind.test.utils.JwtUtil;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Test(expected = DuplicatedIdException.class)
    public void 회원가입_아이디_중복() {
        //given
        UserDto user = getUser();
        //when
        userService.insert(user); //error
    }

    @Test
    public void 회원가입_아이디_토큰발생() {
        //given
        UserDto user = getUser();
        //when
        int getId = userService.insert(user);
        //then
        TokenDto token = userRepository.findToken(getId);
        Assertions.assertThat(token).isNotNull();

    }

    private UserDto getUser() {
        return UserDto.builder().username("test").password("123123").build();
    }

}