package org.jcker.smartqq.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jcker.domain.BaseEntity;

@Entity
@Table(name="groupmessage")
public class GroupMessage extends BaseEntity
{
  private long groupId;
  private long time;
  private String content;
  private long userId;

  public GroupMessage(JSONObject json)
  {
    JSONArray cont = json.getJSONArray("content");

    int size = cont.size();
    StringBuilder contentBuilder = new StringBuilder();
    for (int i = 1; i < size; i++) {
      contentBuilder.append(cont.getString(i));
    }
    this.content = contentBuilder.toString();

    this.time = json.getLongValue("time");
    this.groupId = json.getLongValue("group_code");
    this.userId = json.getLongValue("send_uin");
  }

  public GroupMessage() {
  }

  @Column(name="groupId")
  public long getGroupId() {
    return this.groupId;
  }

  public void setGroupId(long groupId) {
    this.groupId = groupId;
  }
  @Id
  @Column(name="time")
  public long getTime() {
    return this.time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  @Column(name="content")
  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Column(name="userId")
  public long getUserId() {
    return this.userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }
}
