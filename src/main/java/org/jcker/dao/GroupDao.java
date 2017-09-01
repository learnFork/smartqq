package org.jcker.dao;

import org.jcker.smartqq.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by <a href='http://jcker.org'>Alan Turing</a>
 * on 2017-08-29 at 8:23 PM
 *
 * @version 1.0
 */
public interface GroupDao extends JpaRepository<Group, Long> {

    Group findGroupById(long id);
}
