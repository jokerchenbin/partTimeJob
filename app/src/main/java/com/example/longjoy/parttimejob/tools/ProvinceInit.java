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
        p.setCityList(new String[]{"包头市", "乌海市", "赤峰市", "通辽市"
                , "鄂尔多斯市", "呼伦贝尔市", "巴彦淖尔市", "乌兰察布市", "兴安盟", "锡林郭勒市", "阿拉善盟"});
        list.add(p);
        p = new Province();
        p.setProvince_name("辽宁省");
        p.setCityList(new String[]{"沈阳市", "大连市", "鞍山市", "抚顺市"
                , "本溪市", "丹东市", "锦州市", "营口市", "阜新市", "辽阳市", "盘锦市"
                , "铁岭市", "朝阳市", "葫芦岛市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("吉林省");
        p.setCityList(new String[]{"长春市", "吉林市", "四平市", "辽源市"
                , "通化市", "白山市", "松原市", "白城市", "延边"});
        list.add(p);
        p = new Province();
        p.setProvince_name("黑龙江省");
        p.setCityList(new String[]{"哈尔滨市", "齐齐哈尔市", "鹤岗市", "双鸭山市", "鸡西市", "大庆市", "伊春市",
                "牡丹江市", "佳木斯市", "七台河市", "黑河市", "绥化市", "大兴安岭市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("江苏省");
        p.setCityList(new String[]{"南京市", "无锡市", "徐州市", "常州市", "苏州市", "南通市", "连云港市", "淮安市",
                "盐城市", "扬州市", "镇江市", "泰州市", "宿迁市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("浙江省");
        p.setCityList(new String[]{"杭州市", "宁波市", "温州市", "嘉兴市", "湖州市", "绍兴市", "金华市",
                "舟山市", "台州市", "丽水市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("安徽省");
        p.setCityList(new String[]{"合肥市", "芜湖市", "蚌埠市", "淮南市", "马鞍山市", "淮北市", "铜陵市",
                "安庆市", "黄山市", "滁州市", "阜阳市", "宿州市", "巢湖市", "六安市", "亳州市", "池州市", "宣城市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("福建省");
        p.setCityList(new String[]{"福州市", "厦门市", "莆田市", "三明市", "泉州市", "漳州市", "南平市",
                "龙岩市", "宁德市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("江西省");
        p.setCityList(new String[]{"南昌市", "景德镇市", "萍乡市", "新余市", "九江市", "鹰潭市",
                "赣州市", "吉安市", "宜春市", "抚州市", "上饶市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("山东省");
        p.setCityList(new String[]{"济南市", "青岛市", "淄博市", "枣庄市", "东营市", "潍坊市",
                "烟台市", "威海市", "济宁市", "泰安市", "日照市", "莱芜市", "德州市", "临沂市",
                "聊城市", "滨州市", "菏泽市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("河南省");
        p.setCityList(new String[]{"郑州市", "开封市", "洛阳市", "平顶山市", "焦作市", "鹤壁市",
                "新乡市", "安阳市", "濮阳市", "许昌市", "漯河市", "三门峡市", "南阳市", "商丘市",
                "信阳市", "周口市", "驻马店市", "济源市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("湖北省");
        p.setCityList(new String[]{"武汉市", "黄石市", "襄樊市", "十堰市", "荆州市", "宜昌市",
                "荆门市", "鄂州市", "孝感市", "黄冈市", "咸宁市", "随州市", "仙桃市", "天门市",
                "潜江市", "神农架市", "恩施土家族苗族自治州市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("湖南省");
        p.setCityList(new String[]{"长沙市", "株洲市", "湘潭市", "衡阳市", "邵阳市", "岳阳市", "常德市",
                "张家界市", "益阳市", "郴州市", "怀化市", "娄底市", "湘西土家族苗族自治州市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("广东省");
        p.setCityList(new String[]{"广州市", "深圳市", "珠海市", "汕头市", "韶关市", "佛山市",
                "江门市", "湛江市", "茂名市", "肇庆市", "惠州市", "梅州市", "汕尾市", "河源市",
                "阳江市", "清远市", "东莞市", "中山市", "潮州市", "揭阳市", "云浮市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("广西省");
        p.setCityList(new String[]{"南宁市", "柳州市", "桂林市", "梧州市", "北海市", "防城港市",
                "钦州市", "贵港市", "玉林市", "百色市", "贺州市", "河池市", "来宾市", "崇左市"});
        list.add(p);
        p = new Province();
        p.setProvince_name("海南省");
        p.setCityList(new String[]{"海口市", "三亚市", "五指山市", "琼海市", "儋州市", "文昌市",
                "万宁市", "东方市", "澄迈市", "定安市", "屯昌市", "临高市", "白沙黎族自治县昌市",
                "江黎族自治县", "乐东黎族自治县"});
        list.add(p);
        p = new Province();
        p.setProvince_name("四川省");
        p.setCityList(new String[]{"成都市","自贡市","攀枝花市","泸州市","德阳市","绵阳市",
                "广元市","遂宁市","乐江市","乐山市","南充市","宜宾市","达州市",
                "眉山市","雅安市","巴中市","资阳市"});
        list.add(p);
        return list;
    }
}
