package org.jcker.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Discuss
{

  @JSONField(name="did")
  private long id;
  private String name;

  public long getId()
  {
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
}
