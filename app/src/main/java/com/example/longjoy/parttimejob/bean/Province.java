package com.example.longjoy.parttimejob.bean;

/**
 * 类描述：保存省份
 * 创建人：陈彬
 * 创建时间：2015/12/30 14:18
 */
public class Province {

    private String province_name;
    private String [] cityList;

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String[] getCityList() {
        return cityList;
    }

    public void setCityList(String[] cityList) {
        this.cityList = cityList;
    }
}
