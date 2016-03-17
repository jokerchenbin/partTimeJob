package com.example.longjoy.parttimejob.bean;

/**
 * 类描述：城市
 * 创建人：陈彬
 * 创建时间：2015/12/30 14:19
 */
public class City {
    public String name;
    public String pinyi;

    public City(String name, String pinyi) {
        super();
        this.name = name;
        this.pinyi = pinyi;
    }

    public City() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyi() {
        return pinyi;
    }

    public void setPinyi(String pinyi) {
        this.pinyi = pinyi;
    }
}
