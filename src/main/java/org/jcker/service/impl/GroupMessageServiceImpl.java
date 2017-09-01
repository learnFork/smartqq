package org.jcker.service.impl;

import org.jcker.dao.GroupDao;
import org.jcker.dao.GroupMessageDao;
import org.jcker.service.GroupMessageService;
import org.jcker.smartqq.model.Group;
import org.jcker.smartqq.model.GroupMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("groupMessageService")
@Transactional
public class GroupMessageServiceImpl
        implements GroupMessageService {

    @Autowired
    GroupMessageDao groupMessageDao;


    public List<GroupMessage> getGroupMessage() {
        return this.groupMessageDao.findAll();
    }

    public GroupMessage getGroupMessage(GroupMessage groupMessage) {
        return (GroupMessage) this.groupMessageDao.getOne(Long.valueOf(groupMessage.getTime()));
    }

    public void saveGroupMessage(GroupMessage groupMessage) {
        this.groupMessageDao.saveAndFlush(groupMessage);
    }

    public List<GroupMessage> getLatestGroupMessage() {
        return this.groupMessageDao.findAll();
    }

    @Override
    public List<GroupMessage> findAllByUserId(long userId) {
        return this.groupMessageDao.findAllByUserId(userId);
    }

    @Override
    public List<GroupMessage> findAllByGroupId(long groupId) {
        return groupMessageDao.findAllByGroupIdOrderByTime(groupId);
    }

}
