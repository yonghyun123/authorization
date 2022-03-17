package com.caremind.test.service;

import com.caremind.test.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    public int insert(MessageDto message) {
        return 0;
    }

    public List<MessageDto> select(int recipient, int start, int limit) {
        return null;
    }
}
