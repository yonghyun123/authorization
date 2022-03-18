package com.caremind.test.controller;

import com.caremind.test.dto.MessageDto;
import com.caremind.test.dto.UserDto;
import com.caremind.test.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    /**
     * 메세지를 입력한다.
     * @param userDto
     * @param messageDto
     * @return
     */
    @PostMapping("/messages")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> insert(@RequestAttribute(value = "user", required = false) UserDto userDto,
                                                     @RequestBody MessageDto messageDto) {
        if(StringUtils.isEmpty(messageDto.getSender()) || StringUtils.isEmpty(messageDto.getRecipient()) || messageDto.getContent() == null) {
            throw new RuntimeException("Sender, recepient, and content required");
        }
        int id = messageService.insert(messageDto);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("id", id);
        resultMap.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    /**
     * 메세지 리스트를 가지고 온다.
     * @param recipient
     * @param start
     * @param limit
     * @return
     */
    @GetMapping("/messages")
    public ResponseEntity<Map<String, List<MessageDto>>> getMessages(@RequestParam int recipient,
                                                                     @RequestParam int start,
                                                                     @RequestParam(required = false, defaultValue = "100") int limit) {
        List<MessageDto> select = messageService.select(recipient, start, limit);
        Map<String,List<MessageDto>> messageMap = new HashMap<>();
        messageMap.put("messages", select);
        return new ResponseEntity<>(messageMap, HttpStatus.OK);
    }

}
