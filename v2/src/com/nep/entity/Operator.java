package com.nep.entity;

import java.io.Serializable;

/*
@author  JIE
操作员信息，是三个角色的父类
 */
public class Operator implements Serializable {
    private String loginCode;		//登录账号
    private String password ;		//登录密码
    private String realName;		//真实姓名
    //构造方法及setter/getter方法......


    public Operator() {
    }

    public Operator(String loginCode, String password, String realName) {
        this.loginCode = loginCode;
        this.password = password;
        this.realName = realName;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public String getPassword() {
        return password;
    }

    public String getRealName() {
        return realName;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "loginCode='" + loginCode + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
