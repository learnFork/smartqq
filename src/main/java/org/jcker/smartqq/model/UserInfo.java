package org.jcker.smartqq.model;

import com.alibaba.fastjson.annotation.JSONField;

public class UserInfo {
    private Birthday birthday;
    private String phone;
    private String occupation;
    private String college;
    private String uin;
    private int blood;
    private String lnick;
    private String homepage;

    @JSONField(name = "vip_info")
    private int vipInfo;
    private String city;
    private String country;
    private String province;
    private String personal;
    private int shengxiao;
    private String nick;
    private String email;
    private String account;
    private String gender;
    private String mobile;

    public Birthday getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCollege() {
        return this.college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getUin() {
        return this.uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }

    public int getBlood() {
        return this.blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public String getLnick() {
        return this.lnick;
    }

    public void setLnick(String lnick) {
        this.lnick = lnick;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public int getVipInfo() {
        return this.vipInfo;
    }

    public void setVipInfo(int vipInfo) {
        this.vipInfo = vipInfo;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPersonal() {
        return this.personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public int getShengxiao() {
        return this.shengxiao;
    }

    public void setShengxiao(int shengxiao) {
        this.shengxiao = shengxiao;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
