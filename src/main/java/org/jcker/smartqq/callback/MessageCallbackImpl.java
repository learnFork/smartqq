package org.jcker.smartqq.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jcker.controller.Launcher;
import org.jcker.dao.GroupMessageDao;
import org.jcker.framework.JckerWebSocketHandler;
import org.jcker.service.GroupInfoService;
import org.jcker.service.GroupService;
import org.jcker.smartqq.client.SmartQQClient;
import org.jcker.smartqq.model.DiscussMessage;
import org.jcker.smartqq.model.GroupMessage;
import org.jcker.smartqq.model.GroupUser;
import org.jcker.smartqq.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.util.List;

@Service("messageCallback")
public class MessageCallbackImpl
        implements MessageCallback {
    public static final Logger log = Logger.getLogger(MessageCallbackImpl.class);

    @Autowired
    GroupMessageDao groupMessageDao;

    @Autowired
    JckerWebSocketHandler jckerWebSocketHandler;

    @Autowired
    GroupService groupService;

    @Autowired
    GroupInfoService groupInfoService;

    public void onMessage(Message message) {
    }

    public void onGroupMessage(GroupMessage message) {
        try {
            String fmtStr = "";
            if (StringUtils.isEmpty(fmtStr = message.getContent().replaceAll("\\[\"face\",\\d++\\]", ""))) {
                return;
            }
            List<GroupUser> groupUserList = SmartQQClient.groupInfoMap.get(message.getGroupId()).getUsers();
            String nickname = null;
            for (GroupUser groupUser : groupUserList) {
                if (groupUser.getUin() == message.getUserId()) {
                    nickname = groupUser.getNick();
                    break;
                }
            }
            message.setNickname(nickname);
            message.setContent(fmtStr);

            message.setGroupId(SmartQQClient.groupInfoMap.get(message.getGroupId()).getCreatetime());//(groupInfoService.getGroupInfoByGid(message.getGroupId()).getCreatetime());
            ObjectMapper mapper = new ObjectMapper();
            String messageString = mapper.writeValueAsString(message);
            this.jckerWebSocketHandler.handleMessage(null, new TextMessage(messageString));

            this.groupMessageDao.save(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDiscussMessage(DiscussMessage message) {
    }
}
