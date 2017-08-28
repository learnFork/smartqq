package org.jcker.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.ArrayList;
import java.util.List;

public class DiscussInfo
{

  @JSONField(name="did")
  private long id;

  @JSONField(name="discu_name")
  private String name;
  private List<DiscussUser> users = new ArrayList();

  public void addUser(DiscussUser user) {
    this.users.add(user);
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<DiscussUser> getUsers() {
    return this.users;
  }

  public void setUsers(List<DiscussUser> users) {
    this.users = users;
  }
}
