package org.jcker.service;

import org.jcker.smartqq.model.Group;

import java.util.List;

/**
 * Created by <a href='http://jcker.org'>Alan Turing</a>
 * on 2017-08-31 at 2:52 PM
 *
 * @version 1.0
 */
public interface GroupService {
    void saveOrUpdateGroup(List<Group> groupList);
    List<Group> findAllGroup();
    Group getGroupById(long id);

}
