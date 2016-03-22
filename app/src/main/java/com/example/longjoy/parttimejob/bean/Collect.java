package com.example.longjoy.parttimejob.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 类描述：收藏记录
 * 创建人：陈彬
 * 创建时间：2016/3/22 11:04
 */
public class Collect extends BmobObject {

    private MyUser myUser;
    private JobInfo jobInfo;

    public JobInfo getJobInfo() {
        return jobInfo;
    }

    public void setJobInfo(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
    }

    public MyUser getMyUser() {
        return myUser;
    }

    public void setMyUser(MyUser myUser) {
        this.myUser = myUser;
    }


}
