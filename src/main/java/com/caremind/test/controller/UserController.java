package com.caremind.test.controller;

import com.caremind.test.dto.TokenDto;
import com.caremind.test.dto.UserDto;
import com.caremind.test.service.UserService;
import com.caremind.test.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 사용자를 가입한다.
     * @param userDto
     * @return
     */
    @PostMapping("/users/new")
    @ResponseBody
    public ResponseEntity<UserDto> insert(@RequestBody UserDto userDto) {
        if(UserDto.hasNullData(userDto)) {
            throw new NullPointerException("Required username and password");
        }
        int insert = userService.insert(userDto);
        UserDto user = UserDto.builder().id(insert).build();

        return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    /**
     * 로그인 한뒤 토큰을 생성한다.
     * @param userDto
     * @return
     */
    @PostMapping("/users/login")
    @ResponseBody
    public ResponseEntity<TokenDto> login(@RequestBody UserDto userDto) {
        TokenDto tokenDto = userService.login(userDto.getUsername(), userDto.getPassword());
        if(tokenDto == null){
            return new ResponseEntity<TokenDto>(tokenDto, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<TokenDto>(tokenDto, HttpStatus.OK);
        }
    }

    /**
     * 토큰 테스트
     * @param userDto
     * @return
     */
    @PostMapping("/tokentest")
    @ResponseBody
    public ResponseEntity<UserDto> tokenTest(@RequestAttribute("user") UserDto userDto) {

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }
}
