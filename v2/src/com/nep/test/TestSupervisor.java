package com.nep.test;

import com.nep.entity.Supervisor;
import com.nep.io.FileIO;

import java.util.ArrayList;
import java.util.List;

public class TestSupervisor {
    public static void main(String[] args) {
        Supervisor s1 = new Supervisor();
        s1.setLoginCode("202501");
        s1.setPassword("123");
        s1.setRealName("tom");
        s1.setSex("男");
        Supervisor s2 = new Supervisor();
        s2.setLoginCode("202502");
        s2.setPassword("123");
        s2.setRealName("jack");
        s2.setSex("男");
        Supervisor s3 = new Supervisor();
        s3.setLoginCode("202503");
        s3.setPassword("123");
        s3.setRealName("rose");
        s3.setSex("女");
        List<Supervisor> slist = new ArrayList<Supervisor>();
        slist.add(s1);
        slist.add(s2);
        slist.add(s3);

        FileIO.writeObject("supervisor.txt",slist);
    }

}
