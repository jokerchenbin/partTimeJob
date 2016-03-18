package com.example.longjoy.parttimejob.bean;

import cn.bmob.v3.BmobUser;

/**
 * 类描述：用户管理类
 * 创建人：陈彬
 * 创建时间：2015/12/29 17:13
 */
public class MyUser extends BmobUser {

    private String  sex ;
    private int age ;
    private String  imageUrl;
    private String height;//身高
    private String school;//学校


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "sex='" + sex + '\'' +
                ", age=" + age +
                ", imageUrl='" + imageUrl + '\'' +
                ", height='" + height + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
