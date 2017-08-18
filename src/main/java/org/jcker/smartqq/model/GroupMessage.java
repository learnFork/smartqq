package org.jcker.smartqq.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jcker.database.Entity;
import org.jcker.database.annotation.ColumnMapping;
import org.jcker.database.annotation.TableMapping;

/**
 * 群消息.
 *
 * @author ScienJus
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @author <a href="http://helloalanturing.com">Alan Turing</a>
 */
@TableMapping(tableName = "groupmessage", primaryKeyType = "Single", primaryKey = "time")
public class GroupMessage extends Entity {

    @ColumnMapping(columnName = "groupId", columnType = "Long")
    private long groupId;
    @ColumnMapping(columnName = "time", columnType = "Long")
    private long time;
    @ColumnMapping(columnName = "content", columnType = "String")
    private String content;
    @ColumnMapping(columnName = "userId", columnType = "Long")
    private long userId;

//    private Font font;

    public GroupMessage(String jsonString) {
        JSONObject json = JSON.parseObject(jsonString);
        JSONArray cont = json.getJSONArray("content");
//        this.font = cont.getJSONArray(0).getObject(1, Font.class);

        final int size = cont.size();
        final StringBuilder contentBuilder = new StringBuilder();
        for (int i = 1; i < size; i++) {
            contentBuilder.append(cont.getString(i));
        }
        this.content = contentBuilder.toString();

        this.time = json.getLongValue("time");
        this.groupId = json.getLongValue("group_code");
        this.userId = json.getLongValue("send_uin");
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

/*    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }*/

}
