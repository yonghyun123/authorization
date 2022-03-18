package com.caremind.test.service;

import com.caremind.test.dto.TokenDto;
import com.caremind.test.dto.UserDto;
import com.caremind.test.exception.DuplicatedIdException;
import com.caremind.test.repository.UserRepository;
import com.caremind.test.utils.JwtUtil;
import com.caremind.test.utils.SHA256Encrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    /**
     * 회원검색
     * @param username
     * @return
     */
    public UserDto findUser(String username) {
        return null;
    }

    /**
     * 회원가입 메서드
     * 입력받은 비밀번호를 SHA256 암호화를 해서 저장
     * @param user
     * @return
     */
    @Transactional
    public int insert(UserDto user) {
        if(isDuplicated(user.getUsername())){
            throw new DuplicatedIdException("중복된 아이디가 존재합니다.");
        }
        user.setPassword(SHA256Encrypt.encryptSHA256(user.getPassword()));
        int userId = userRepository.insert(user);
        userRepository.insertToken(userId, jwtUtil.createToken());
        return userId;
    }

    /**
     * 토큰생성 메서드
     * @param userId
     * @param token
     */
    @Transactional
    public void insertToken(int userId, String token){
        userRepository.insertToken(userId, token);
    }

    /**
     * 로그인 메서드
     * @param username 고객 아이디
     * @param password 고객 비밀번호
     * @return
     */
    @Transactional
    public TokenDto login(String username, String password){
        String encPasswd = SHA256Encrypt.encryptSHA256(password);
        UserDto user = userRepository.findByNameAndPassword(username, encPasswd);
        if(user == null){
            throw new RuntimeException("회원정보가 없습니다.");
        }

        return userRepository.findToken(user.getId());
    }

    /**
     * 중복된 회원이 있는지 체크
     * @param username
     * @return
     */
    public boolean isDuplicated(String username){
        return userRepository.findUser(username) != null;
    }
}