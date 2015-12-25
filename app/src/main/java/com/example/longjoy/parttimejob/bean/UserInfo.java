package com.example.longjoy.parttimejob.bean;

import cn.bmob.v3.BmobObject;

/**
 * 类描述：用户注册的基本信息
 * 创建人：陈彬
 * 创建时间：2015/12/25 14:33
 */
public class UserInfo extends BmobObject {
    /* 用户名 密码  电话号码 */
    private String telephone;
    private String username;
    private String password;
    private String imageUrl = "";

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "telephone='" + telephone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
