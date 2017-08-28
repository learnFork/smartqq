package org.jcker.smartqq.model;

import java.util.ArrayList;
import java.util.List;

public class GroupInfo {
    private long gid;
    private long createtime;
    private String memo;
    private String name;
    private long owner;
    private String markname;
    private List<GroupUser> users = new ArrayList();

    public void addUser(GroupUser user) {
        this.users.add(user);
    }

    public long getGid() {
        return this.gid;
    }

    public void setGid(long gid) {
        this.gid = gid;
    }

    public long getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwner() {
        return this.owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public String getMarkname() {
        return this.markname;
    }

    public void setMarkname(String markname) {
        this.markname = markname;
    }

    public List<GroupUser> getUsers() {
        return this.users;
    }

    public void setUsers(List<GroupUser> users) {
        this.users = users;
    }
}
