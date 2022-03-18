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


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void 회원가입_테스트(){

        //given
        UserDto user = getUser();

        //when
        int insert = userRepository.insert(user);
        UserDto user1 = userRepository.findUser(user.getUsername());

        //then

        Assertions.assertThat(user.getUsername()).isEqualTo(user1.getUsername());
    }

    private UserDto getUser() {
        return UserDto.builder().username("lee").password("123123").build();
    }


}