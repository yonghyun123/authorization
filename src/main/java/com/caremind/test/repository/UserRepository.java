package com.caremind.test.repository;

import com.caremind.test.dto.TokenDto;
import com.caremind.test.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Slf4j
public class UserRepository {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int insert(UserDto user) {
        int re = this.jdbcTemplate.update("insert into users (user_name, password) values (?,?)",
                user.getUsername(),
                user.getPassword()
        );
        if (re == 1) {
            return findUser(user.getUsername()).getId();
        } else {
            throw new RuntimeException("inert error");
        }
    }

    public void insertToken(int userId, String token) {
        this.jdbcTemplate.update("insert into token (user_id, token) values (?,?)",
                userId,
                token
        );
    }

    public TokenDto findToken(int userId) {
        return this.jdbcTemplate.queryForObject("select * from token where user_id = ?", new Integer[]{userId},
                (resultSet, i) -> {
                    TokenDto token = TokenDto.builder().id(resultSet.getInt("user_id"))
                            .token(resultSet.getString("token"))
                            .build();
                    return token;
                }
        );
    }


    public UserDto findUser(String username) {
        List<UserDto> userDto = this.jdbcTemplate.query("select * from users where user_name = ?", new String[]{username},
                (resultSet, i) -> {
                    UserDto user = UserDto.builder().id(resultSet.getInt("id"))
                            .username(resultSet.getString("user_name"))
                            .build();
                    log.info("user = {}", user);
                    return user;
                }
        );
        return userDto.isEmpty() ? null : userDto.get(0);

    }

    public UserDto findByNameAndPassword(String username, String encPasswd) {
        return this.jdbcTemplate.queryForObject("select * from users where user_name = ? and password = ?"
                , new String[]{username, encPasswd}
                , (resultSet, i) -> {
                    UserDto user = UserDto.builder().id(resultSet.getInt("id"))
                            .username(resultSet.getString("user_name"))
                            .build();
                    log.info("user = {}", user);
                    return user;
                }
        );
    }
}