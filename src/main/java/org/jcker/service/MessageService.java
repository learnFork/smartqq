package org.jcker.service;

import java.util.List;
import org.jcker.smartqq.model.Message;

public interface MessageService
{
  Message findMessageByTime(long paramLong);

  void saveOrUpdateMessage(Message paramMessage);

  List<Message> findMessageByUserId(Long paramLong);
}
