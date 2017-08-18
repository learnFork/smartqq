package org.jcker.web.dao.impl;

import org.jcker.database.BaseDao;
import org.jcker.web.dao.FriendMessageDao;
import org.jcker.smartqq.model.Message;
import org.springframework.stereotype.Repository;

@Repository("friendMessageDao")
public class FriendMessageDaoImpl extends BaseDao implements FriendMessageDao {
    @Override
    public Message createMessage(Message message) {
        return super.insert(message);
    }
}
