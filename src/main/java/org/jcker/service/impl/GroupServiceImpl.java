package org.jcker.service.impl;

import org.jcker.dao.GroupDao;
import org.jcker.service.GroupService;
import org.jcker.smartqq.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by <a href='http://jcker.org'>Alan Turing</a>
 * on 2017-08-31 at 2:53 PM
 *
 * @version 1.0
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupDao groupDao;

    @Override
    public void saveOrUpdateGroup(List<Group> groupList) {
        groupDao.save(groupList);
    }

    @Override
    public List<Group> findAllGroup() {
        return groupDao.findAll();
    }

    @Override
    public Group getGroupById(long id) {
        return groupDao.findGroupById(id);
    }

}
