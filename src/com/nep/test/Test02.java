package com.nep.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class Test02 {
    public static void main(String[] args) throws IOException {
        OutputStream out =new FileOutputStream("a.txt");

        out.write(97);
        out.write(65);

        out.close();
    }
}
