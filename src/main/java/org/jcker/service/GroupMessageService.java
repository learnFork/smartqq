package org.jcker.service;

import java.util.List;

import org.jcker.smartqq.model.Group;
import org.jcker.smartqq.model.GroupMessage;

public interface GroupMessageService {
    List<GroupMessage> getGroupMessage();

    GroupMessage getGroupMessage(GroupMessage paramGroupMessage);

    void saveGroupMessage(GroupMessage paramGroupMessage);

    List<GroupMessage> getLatestGroupMessage();

    List<GroupMessage> findAllByUserId(long userId);

    List<GroupMessage> findAllByGroupId(long groupId);

}
