package org.jcker.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.jcker.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groupentity")
public class Group extends BaseEntity {

    @JSONField(name = "gid")
    private long id;
    private String name;
    private long flag;
    private long code;

    @Column(name = "id")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "flag")
    public long getFlag() {
        return this.flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
    }

    @Column(name = "code")
    public long getCode() {
        return this.code;
    }

    public void setCode(long code) {
        this.code = code;
    }
}
