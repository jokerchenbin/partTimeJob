package com.example.longjoy.parttimejob.bean;

import cn.bmob.v3.BmobUser;

/**
 * 类描述：用户管理类
 * 创建人：陈彬
 * 创建时间：2015/12/29 17:13
 */
public class MyUser extends BmobUser {

    private String  sex = "男";
    private Integer age = 18;
    private String  imageUrl;


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
