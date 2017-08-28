package org.jcker.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

public class FriendStatus
{
  private long uin;
  private String status;

  @JSONField(name="client_type")
  private int clientType;

  public long getUin()
  {
    return this.uin;
  }

  public void setUin(long uin) {
    this.uin = uin;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getClientType() {
    return this.clientType;
  }

  public void setClientType(int clientType) {
    this.clientType = clientType;
  }
}
