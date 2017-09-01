package org.jcker.dao;

import org.jcker.smartqq.model.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by <a href='http://jcker.org'>Alan Turing</a>
 * on 2017-08-31 at 8:25 PM
 *
 * @version 1.0
 */
public interface GroupInfoDao extends JpaRepository<GroupInfo, Long> {
    GroupInfo getGroupInfoByGid(long gid);
}
