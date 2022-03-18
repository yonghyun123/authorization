package com.caremind.test.repository;

import com.caremind.test.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

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


    public UserDto findUser(String username) {
        return this.jdbcTemplate.queryForObject("select * from users where user_name = ?", new String[]{username},
                (resultSet, i) -> {
                    UserDto user = UserDto.builder().id(resultSet.getInt("id"))
                            .username(resultSet.getString("user_name"))
                            .build();
                    log.info("user = {}",user);
                    return user;
                }
        );
    }
}