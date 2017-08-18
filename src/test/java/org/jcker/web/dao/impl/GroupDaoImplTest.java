package org.jcker.web.dao.impl;

import org.jcker.web.dao.GroupMessageDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.List;

public class GroupDaoImplTest {
    @Autowired
    private GroupMessageDao groupDao;

    @org.junit.Test
    public void createGroupMessage() throws Exception {
    }

    @org.junit.Test
    public void queryGroupMessageByTime() throws Exception {
        List list = groupDao.queryGroupMessageByTime(new Date(System.currentTimeMillis()));
        System.out.println("list = " + list);
    }

}