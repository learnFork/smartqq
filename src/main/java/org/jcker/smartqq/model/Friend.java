package org.jcker.smartqq.model;

public class Friend
{
  private long userId;
  private String markname = "";
  private String nickname;
  private boolean vip;
  private int vipLevel;

  public String toString()
  {
    return "Friend{userId=" + this.userId + ", markname='" + this.markname + '\'' + ", nickname='" + this.nickname + '\'' + ", vip=" + this.vip + ", vipLevel=" + this.vipLevel + '}';
  }

  public long getUserId()
  {
    return this.userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getMarkname() {
    return this.markname;
  }

  public void setMarkname(String markname) {
    this.markname = markname;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public boolean isVip() {
    return this.vip;
  }

  public void setVip(boolean vip) {
    this.vip = vip;
  }

  public int getVipLevel() {
    return this.vipLevel;
  }

  public void setVipLevel(int vipLevel) {
    this.vipLevel = vipLevel;
  }
}
