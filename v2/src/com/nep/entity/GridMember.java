package com.nep.entity;

import java.io.Serializable;

/*
@author  JIE
网格员信息
 */

public class GridMember extends Operator implements Serializable {
    private String gmTel;	//网格员联系电话
    private String state;	//网格员状态: 工作中,休假中
    //构造方法及setter/getter方法......

    public GridMember() {
    }

    public GridMember(String gmTel) {
        this.gmTel = gmTel;
    }

    public GridMember(String loginCode, String password, String realName, String gmTel) {
        super(loginCode, password, realName);
        this.gmTel = gmTel;
    }

    public void setGmTel(String gmTel) {
        this.gmTel = gmTel;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGmTel() {
        return gmTel;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "GridMember{" +
                "gmTel='" + gmTel + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
