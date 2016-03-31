package com.example.longjoy.parttimejob.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/3/31 14:55
 */
public class AD extends BmobObject {

    private String content;//图片消息
    private BmobFile file;


    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
