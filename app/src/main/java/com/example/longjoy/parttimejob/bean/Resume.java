package com.example.longjoy.parttimejob.bean;

import cn.bmob.v3.BmobObject;

/**
 * 类描述：我的简历
 * 创建人：陈彬
 * 创建时间：2016/1/5 11:49
 */
public class Resume extends BmobObject{
    private String name;
    private String age;
    private String sex;
    private String school;
    private String hight;
    private String wantJob;
    private String selfIntroduction;
    private String emailAddr;
    private String QQ;
    private String telephone;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getWantJob() {
        return wantJob;
    }

    public void setWantJob(String wantJob) {
        this.wantJob = wantJob;
    }

    public String getHight() {
        return hight;
    }

    public void setHight(String hight) {
        this.hight = hight;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Resume{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", school='" + school + '\'' +
                ", hight='" + hight + '\'' +
                ", wantJob='" + wantJob + '\'' +
                ", selfIntroduction='" + selfIntroduction + '\'' +
                ", emailAddr='" + emailAddr + '\'' +
                ", QQ='" + QQ + '\'' +
                ", telephone='" + telephone + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
