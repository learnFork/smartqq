package org.jcker.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Group
{

  @JSONField(name="gid")
  private long id;
  private String name;
  private long flag;
  private long code;

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

  public long getFlag() {
    return this.flag;
  }

  public void setFlag(long flag) {
    this.flag = flag;
  }

  public long getCode() {
    return this.code;
  }

  public void setCode(long code) {
    this.code = code;
  }
}
