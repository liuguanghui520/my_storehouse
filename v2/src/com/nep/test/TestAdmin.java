package com.nep.test;

import com.nep.entity.Admin;
import com.nep.io.FileIO;

import java.util.ArrayList;
import java.util.List;

public class TestAdmin {
    public static void main(String[] args) {
        //管理员账号初始化
        Admin a1 = new Admin();
        a1.setLoginCode("1001");
        a1.setPassword("111");
        a1.setRealName("付");
        Admin a2 = new Admin();
        a2.setLoginCode("1002");
        a2.setPassword("222");
        a2.setRealName("陈");
        List<Admin> alist = new ArrayList<Admin>();
        alist.add(a1);
        alist.add(a2);
        FileIO.writeObject("admin.txt", alist);
    }
}
