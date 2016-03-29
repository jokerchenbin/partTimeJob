package com.example.longjoy.parttimejob.bean;

import cn.bmob.v3.BmobObject;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/3/29 22:51
 */
public class Suggest extends BmobObject {

    private String comment;//内容
    private MyUser user;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
