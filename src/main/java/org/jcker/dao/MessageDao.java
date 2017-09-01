package org.jcker.dao;

import java.util.List;

import org.jcker.smartqq.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageDao extends JpaRepository<Message, Long> {
    List<Message> findMessagesByUserId(Long paramLong);
}
