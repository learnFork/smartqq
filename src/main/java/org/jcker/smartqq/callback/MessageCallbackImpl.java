package org.jcker.smartqq.callback;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jcker.dao.GroupMessageDao;
import org.jcker.framework.JckerWebSocketHandler;
import org.jcker.smartqq.model.DiscussMessage;
import org.jcker.smartqq.model.GroupMessage;
import org.jcker.smartqq.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

@Service("messageCallback")
public class MessageCallbackImpl
  implements MessageCallback
{
  public static final Logger log = Logger.getLogger(MessageCallbackImpl.class);

  @Autowired
  GroupMessageDao groupMessageDao;

  @Autowired
  JckerWebSocketHandler jckerWebSocketHandler;

  public void onMessage(Message message) {
  }

  public void onGroupMessage(GroupMessage message) {
    try {
      String fmtStr = "";
      if (StringUtils.isEmpty(fmtStr = message.getContent().replaceAll("\\[\"face\",\\d++\\]", "")))
      {
        return;
      }
      log.info(fmtStr);
      message.setContent(fmtStr);

      this.jckerWebSocketHandler.handleMessage(null, new TextMessage(fmtStr));

      this.groupMessageDao.save(message);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void onDiscussMessage(DiscussMessage message)
  {
  }
}
