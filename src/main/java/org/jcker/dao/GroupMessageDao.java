package org.jcker.dao;

import org.jcker.smartqq.model.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract interface GroupMessageDao extends JpaRepository<GroupMessage, Long>
{
}
