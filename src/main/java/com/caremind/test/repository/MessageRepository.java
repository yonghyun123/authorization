package com.caremind.test.repository;

import com.caremind.test.dto.ContentDto;
import com.caremind.test.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository
public class MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public int insertMessage(MessageDto message, int contentId) {
        return this.jdbcTemplate.update("insert into message (sender, recipient, content_id, date) values (?,?,?,?)",
                message.getSender(),
                message.getRecipient(),
                contentId,
                message.getTimestamp().toString()
        );
    }

    public int insertContent(ContentDto content){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("insert into content (type, url, text) values (?,?,?)"
                    ,new String[]{"id"});
            ps.setString(1, content.getType());
            ps.setString(2, content.getUrl());
            ps.setString(3, content.getText());
            return ps;

        }, keyHolder);
        return (int)keyHolder.getKey().longValue();
    }

    public List<MessageDto> select(int recipient, int start, int limit) {
        return this.jdbcTemplate.query("select * " +
                        "                   from message a, content b" +
                        "                   where a.content_id = b.id" +
                        "                   and  a.recipient = ?" +
                        "                   order by date desc"
                , new Integer[]{recipient}
                , (rs, rowNum) -> {
                    MessageDto dto = new MessageDto();
                    ContentDto contentDto = new ContentDto();
                    contentDto.setText("text");
                    contentDto.setType("type");
                    contentDto.setUrl("url");

                    dto.setContent(contentDto);
                    dto.setId(rs.getInt("id"));
                    dto.setSender(rs.getInt("sender"));
                    dto.setRecipient(rs.getInt("recipient"));
                    String date = rs.getString("date");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");

                    try {
                        dto.setTimestamp(format.parse(rs.getString("date")));
                    } catch (ParseException e) {
                        throw new RuntimeException("알 수 없는 날짜형식입니다.");
                    }
                    return dto;
                });
    }

}

