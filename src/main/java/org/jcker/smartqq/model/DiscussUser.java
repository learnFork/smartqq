package org.jcker.smartqq.model;

public class DiscussUser
{
  private long uin;
  private String nick;
  private int clientType;
  private String status;

  public String toString()
  {
    return "DiscussUser{uin=" + this.uin + ", nick='" + this.nick + '\'' + ", clientType='" + this.clientType + '\'' + ", status='" + this.status + '\'' + '}';
  }

  public long getUin()
  {
    return this.uin;
  }

  public void setUin(long uin) {
    this.uin = uin;
  }

  public String getNick() {
    return this.nick;
  }

  public void setNick(String nick) {
    this.nick = nick;
  }

  public int getClientType() {
    return this.clientType;
  }

  public void setClientType(int clientType) {
    this.clientType = clientType;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
