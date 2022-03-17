package com.caremind.test.repository;

import com.caremind.test.dto.UserDto;
import org.apache.catalina.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
//    @Rollback(value = false)
    void 회원가입_테스트(){

        //given
        UserDto user = new UserDto();
        user.setUsername("aaa");
        user.setPassword("123123");

        //when
        int insert = userRepository.insert(user);
        UserDto user1 = userRepository.findUser(user.getUsername());

        //then

        Assertions.assertThat(user.getUsername()).isEqualTo(user1.getUsername());
    }



}