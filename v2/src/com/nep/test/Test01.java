package com.nep.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Test01 {
    public static void main(String[] args) throws IOException {
        InputStream in =new FileInputStream("a.txt");

        int a=in.read();
        System.out.println(a);
        a=in.read();
        System.out.println(a);

        in.close();
    }
}
