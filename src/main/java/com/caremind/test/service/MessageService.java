package com.caremind.test.service;

import com.caremind.test.dto.MessageDto;
import com.caremind.test.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public int insert(MessageDto message) {
        int contentId = messageRepository.insertContent(message.getContent());

        message.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        log.info("timestamp = {} ", message.getTimestamp());
        log.info("timestamp2 = {} ", LocalDateTime.now());
        return messageRepository.insertMessage(message, contentId);
    }

    public List<MessageDto> select(int recipient, int start, int limit) {
        return messageRepository.select(recipient, start, limit);
    }
}
