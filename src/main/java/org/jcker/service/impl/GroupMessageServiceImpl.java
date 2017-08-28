package org.jcker.service.impl;

import java.util.List;
import org.jcker.dao.GroupMessageDao;
import org.jcker.service.GroupMessageService;
import org.jcker.smartqq.model.GroupMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("groupMessageService")
@Transactional
public class GroupMessageServiceImpl
  implements GroupMessageService
{

  @Autowired
  GroupMessageDao groupMessageDao;

  public List<GroupMessage> getGroupMessage()
  {
    return this.groupMessageDao.findAll();
  }

  public GroupMessage getGroupMessage(GroupMessage groupMessage) {
    return (GroupMessage)this.groupMessageDao.getOne(Long.valueOf(groupMessage.getTime()));
  }

  public void saveGroupMessage(GroupMessage groupMessage) {
    this.groupMessageDao.saveAndFlush(groupMessage);
  }

  public List<GroupMessage> getLatestGroupMessage()
  {
    return this.groupMessageDao.findAll();
  }
}
