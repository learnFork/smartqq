package org.jcker.web.dao;

import org.jcker.smartqq.model.GroupMessage;

import java.sql.Date;
import java.util.List;

public interface GroupMessageDao {
    GroupMessage createGroupMessage(GroupMessage groupMessage);

    /**
     * 查询群消息，如果date为null，默认使用当前日期。
     * @param date 日期
     * @return 返回查询到的群消息
     */
    List<GroupMessage> queryGroupMessageByTime(Date date);

    List<GroupMessage> getLatestMessage();
}
