package org.jcker.smartqq.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jcker.domain.BaseEntity;

@Entity
@Table(name="message")
public class Message extends BaseEntity
{
  private long time;
  private String content;
  private long userId;

  public Message() {
    super();
  }

  public Message(JSONObject json)
  {
    JSONArray cont = json.getJSONArray("content");

    int size = cont.size();
    StringBuilder contentBuilder = new StringBuilder();
    for (int i = 1; i < size; i++) {
      contentBuilder.append(cont.getString(i));
    }
    this.content = contentBuilder.toString();

    this.time = json.getLongValue("time");
    this.userId = json.getLongValue("from_uin");
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
