package org.jcker.service.impl;

import java.util.List;
import org.jcker.dao.MessageDao;
import org.jcker.service.MessageService;
import org.jcker.smartqq.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("messageService")
@Transactional
public class MessageServiceImpl
  implements MessageService
{

  @Autowired
  MessageDao messageDao;

  public Message findMessageByTime(long time)
  {
    return (Message)this.messageDao.findOne(Long.valueOf(time));
  }

  public void saveOrUpdateMessage(Message message)
  {
    this.messageDao.saveAndFlush(message);
  }

  public List<Message> findMessageByUserId(Long userId)
  {
    return this.messageDao.findMessagesByUserId(userId);
  }
}
