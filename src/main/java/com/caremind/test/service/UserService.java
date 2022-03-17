package com.caremind.test.service;

import com.caremind.test.dto.UserDto;
import com.caremind.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public int insert(UserDto user) {
        return userRepository.insert(user);
    }

    public UserDto findUser(String username) {
        return null;
    }


}