package org.jcker.dao;

import org.jcker.smartqq.model.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMessageDao extends JpaRepository<GroupMessage, Long> {

    List<GroupMessage> findAllByUserId(long userId);

    List<GroupMessage> findAllByGroupIdOrderByTime(long groupId);
}
