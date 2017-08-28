package org.jcker.dao;

import java.util.List;
import org.jcker.smartqq.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract interface MessageDao extends JpaRepository<Message, Long>
{
  public abstract List<Message> findMessagesByUserId(Long paramLong);
}
