package org.jcker.web.dao.impl;

import org.jcker.database.BaseDao;
import org.jcker.web.dao.GroupMessageDao;
import org.jcker.smartqq.model.GroupMessage;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
@Repository("groupMessageDao")
public class GroupMessageDaoImpl extends BaseDao implements GroupMessageDao {
    private void init(){
        try {
            super.createTable(GroupMessage.class);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public GroupMessage createGroupMessage(GroupMessage groupMessage) {
/*        jdbcTemplate.update("INSERT INTO groupmessage(time, groupId, userId, content) VALUES ("
                + groupMessage.getTime() + ","
                + groupMessage.getGroupId() + ","
                + groupMessage.getUserId() + ",'"
                + groupMessage.getContent() + "'"
                + ")");*/
        super.insert(groupMessage);
        return groupMessage;
    }

    @Override
    public List<GroupMessage> queryGroupMessageByTime(Date date) {
        StringBuffer sql = new StringBuffer("select * from mgroupmessage t where 1=1 and ");
        if (date == null){
            date = new Date(System.currentTimeMillis());
            sql.append(" t.time >= ").append(date);
        }
        return jdbcTemplate.queryForList(sql.toString(), GroupMessage.class);
    }

    @Override
    public List<GroupMessage> getLatestMessage() {
        return null;
    }

}
