package com.caremind.test.service;

import com.caremind.test.dto.UserDto;
import com.caremind.test.exception.DuplicatedIdException;
import com.caremind.test.repository.UserRepository;
import com.caremind.test.utils.SHA256Encrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
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

        return userRepository.insert(user);
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