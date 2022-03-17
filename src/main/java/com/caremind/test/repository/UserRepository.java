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
            int userId =1 ;// 저장한 사용자의 아이디 가지고 오기
            return userId;
        } else {
            throw new RuntimeException("inert error");
        }
    }


    public UserDto findUser(String username) {
        return this.jdbcTemplate.queryForObject("select * from users where user_name = ?", new String[]{username},
                (resultSet, i) -> {
                    UserDto user = new UserDto();
                    user.setId(resultSet.getInt("id"));
                    user.setUsername(resultSet.getString("user_name"));
                    log.info("user = {}",user);
                    return user;
                }
        );
    }
}