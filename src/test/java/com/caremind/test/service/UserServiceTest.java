package com.caremind.test.service;

import com.caremind.test.dto.UserDto;

import com.caremind.test.exception.DuplicatedIdException;
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

    @Test(expected = DuplicatedIdException.class)
    public void 회원가입_아이디_중복() {
        //given
        UserDto user = getUser();
        //when
        userService.insert(user); //error
    }

    private UserDto getUser() {
        return UserDto.builder().username("lee").password("123123").build();
    }

}