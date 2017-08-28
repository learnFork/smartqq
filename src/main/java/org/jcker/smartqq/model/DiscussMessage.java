package org.jcker.smartqq.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DiscussMessage {
    private long discussId;
    private long time;
    private String content;
    private long userId;
    private Font font;

    public DiscussMessage(JSONObject json) {
        JSONArray content = json.getJSONArray("content");
        this.font = ((Font) content.getJSONArray(0).getObject(1, Font.class));
        this.content = content.getString(1);
        if (content.size() > 2)
            this.content += content.getString(3);
        this.time = json.getLongValue("time");
        this.discussId = json.getLongValue("did");
        this.userId = json.getLongValue("send_uin");
    }

    public long getDiscussId() {
        return this.discussId;
    }

    public void setDiscussId(long discussId) {
        this.discussId = discussId;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
