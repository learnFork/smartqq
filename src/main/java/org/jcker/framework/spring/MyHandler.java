package org.jcker.framework.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jcker.web.dao.GroupMessageDao;
import org.jcker.smartqq.model.GroupMessage;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.sql.Date;
import java.util.List;

@Service
public class MyHandler extends AbstractWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyHandler.class);

    @Autowired
    GroupMessageDao groupMessageDao;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received message: " + message.getPayload());
        List<GroupMessage> list = groupMessageDao.queryGroupMessageByTime(new Date(System.currentTimeMillis()));
        Assert.assertNull(list);
        GroupMessage groupMessage = list.get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String messageString = objectMapper.writeValueAsString(groupMessage);
        session.sendMessage(new TextMessage(messageString));
    }

}
