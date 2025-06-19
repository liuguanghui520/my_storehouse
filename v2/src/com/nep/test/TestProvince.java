package com.nep.test;

import com.nep.entity.ProvinceCity;
import com.nep.io.FileIO;

import java.util.ArrayList;
import java.util.List;

public class TestProvince {
    public static void main(String[] args) {
        ProvinceCity p1 = new ProvinceCity();
        p1.setProvinceId(1001);
        p1.setProvinceName("辽宁省");
        List<String> city1 = new ArrayList<String>();
        city1.add("沈阳市");
        city1.add("大连市");
        city1.add("鞍山市");
        city1.add("抚顺市");
        p1.setCityName(city1);

        ProvinceCity p2 = new ProvinceCity();
        p2.setProvinceId(1002);
        p2.setProvinceName("吉林省");
        List<String> city2 = new ArrayList<String>();
        city2.add("长春市");
        city2.add("吉林市");
        city2.add("四平市");
        p2.setCityName(city2);

        ProvinceCity p3 = new ProvinceCity();
        p3.setProvinceId(1003);
        p3.setProvinceName("黑龙江省");
        List<String> city3 = new ArrayList<String>();
        city3.add("哈尔滨市");
        city3.add("大庆市");
        city3.add("齐齐哈尔市");
        p3.setCityName(city3);

        List<ProvinceCity> plist = new ArrayList<ProvinceCity>();
        plist.add(p1);
        plist.add(p2);
        plist.add(p3);
        FileIO.writeObject("province_city.txt", plist);
    }

}
