package org.jcker.service;

import org.jcker.smartqq.model.GroupInfo;

import java.util.List;

/**
 * Created by <a href='http://jcker.org'>Alan Turing</a>
 * on 2017-08-31 at 8:23 PM
 *
 * @version 1.0
 */
public interface GroupInfoService {
    void saveOrUpdateGroupInfo(List<GroupInfo> groupInfoList);

    GroupInfo getGroupInfoByGid(long gid);

    void deleteAll();
}
