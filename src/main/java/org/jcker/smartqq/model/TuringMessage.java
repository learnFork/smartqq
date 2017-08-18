package org.jcker.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import org.jcker.database.Entity;

/**
 * Created by Alan Turing on 2017/7/16.
 * turing message entity
 */
@JSONType(ignores = {"userid"})
public class TuringMessage extends Entity {
    private String userid;
    @JSONField
    private String code;
    @JSONField
    private String text;
    @JSONField
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
