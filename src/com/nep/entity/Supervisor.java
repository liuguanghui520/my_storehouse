package com.nep.entity;

import java.io.Serializable;

/*
@author  JIE
公众监督员信息
 */
public class Supervisor extends Operator implements Serializable {
    private String sex;

    public Supervisor() {
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "Supervisor{" +
                "sex='" + sex + '\'' +
                '}';
    }
}
