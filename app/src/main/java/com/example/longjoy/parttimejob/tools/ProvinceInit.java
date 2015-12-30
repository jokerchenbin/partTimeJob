package com.example.longjoy.parttimejob.tools;

import com.example.longjoy.parttimejob.bean.City;
import com.example.longjoy.parttimejob.bean.Province;

import java.util.ArrayList;

/**
 * 类描述：生成省份的工具类
 * 创建人：陈彬
 * 创建时间：2015/12/30 15:20
 */
public class ProvinceInit {

    public static ArrayList<Province> initPrivince(){
        ArrayList<Province> list = new ArrayList<>();
        Province p = new Province();
        p.setProvince_name("北京市");
        list.add(p);
        p = new Province();
        p.setProvince_name("上海市");
        list.add(p);
        p = new Province();
        p.setProvince_name("天津市");
        list.add(p);
        p = new Province();
        p.setProvince_name("重庆市");
        list.add(p);
        p = new Province();
        p.setProvince_name("河北省");
        p.setCityList(new String[]{"石家庄市", "唐山市", "秦皇岛市", "邯郸市"
                , "邢台市", "保定市", "张家口市", "承德市", "沧州市", "廊坊市", "衡水市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("山西省");
        p.setCityList(new String[]{"太原市", "大同市", "阳泉市", "长治市"
                , "晋城市", "朔州市", "晋中市", "运城市", "忻州市", "临汾市", "吕梁市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("内蒙古");
        p.setCityList(new String[]{"包头市","乌海市","赤峰市","通辽市"
                ,"鄂尔多斯市","呼伦贝尔市","巴彦淖尔市","乌兰察布市","兴安盟","锡林郭勒市","阿拉善盟"});
        list.add(p);
        p = new Province();
        p.setProvince_name("辽宁省");
        p.setCityList(new String[]{"沈阳市","大连市","鞍山市","抚顺市"
                ,"本溪市","丹东市","锦州市","营口市","阜新市","辽阳市","盘锦市"
                ,"铁岭市","朝阳市","葫芦岛市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("吉林省");
        p.setCityList(new String[]{"长春市","吉林市","四平市","辽源市"
                ,"通化市","白山市","松原市","白城市","延边"});
        list.add(p);
        return list;
    }
}
