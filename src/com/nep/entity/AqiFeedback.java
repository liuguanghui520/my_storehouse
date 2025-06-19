package com.nep.entity;

import java.io.Serializable;

/*
@author  JIE
AQI反馈
 */
public class AqiFeedback implements Serializable {
    private Integer afId;		//反馈信息编号
    private String afName;	//公众监督员姓名
    private String provinceName;	//省网格区域
    private String cityName;	//市网格区域
    private String address;		//具体地址
    private String information;	//详细反馈信息
    private String estimateGrade;//预估等级
    private String date;		//反馈日期
    private String state;		//反馈状态: 未指派,已指派,已实测
    private String gmName;		//指派网格员
    private String confirmDate;	//实测日期
    private Double so2;			//实测二氧化硫浓度
    private Double co;			//实测一氧化碳浓度
    private Double pm;			//实测PM2.5浓度
    private String confirmLevel;//实测AQI等级
    private String confirmExplain;//实测AQI等级描述

    public AqiFeedback() {
    }

    public AqiFeedback(Integer afId, String afName, String proviceName, String cityName, String address, String infomation, String estimateGrade, String date, String state, String gmName, String confirmDate, Double so2, Double co, Double pm, String confirmLevel, String confirmExplain) {
        this.afId = afId;
        this.afName = afName;
        this.provinceName = proviceName;
        this.cityName = cityName;
        this.address = address;
        this.information = infomation;
        this.estimateGrade = estimateGrade;
        this.date = date;
        this.state = state;
        this.gmName = gmName;
        this.confirmDate = confirmDate;
        this.so2 = so2;
        this.co = co;
        this.pm = pm;
        this.confirmLevel = confirmLevel;
        this.confirmExplain = confirmExplain;
    }

    public void setAfId(Integer afId) {
        this.afId = afId;
    }

    public void setAfName(String afName) {
        this.afName = afName;
    }

    public void setProvinceName(String proviceName) {
        this.provinceName = proviceName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInformation(String infomation) {
        this.information = infomation;
    }

    public void setEstimateGrade(String estimateGrade) {
        this.estimateGrade = estimateGrade;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setGmName(String gmName) {
        this.gmName = gmName;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public void setSo2(Double so2) {
        this.so2 = so2;
    }

    public void setCo(Double co) {
        this.co = co;
    }

    public void setPm(Double pm) {
        this.pm = pm;
    }

    public void setConfirmLevel(String confirmLevel) {
        this.confirmLevel = confirmLevel;
    }

    public void setConfirmExplain(String confirmExplain) {
        this.confirmExplain = confirmExplain;
    }

    public Integer getAfId() {
        return afId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public String getAddress() {
        return address;
    }

    public String getInformation() {
        return information;
    }

    public String getAfName() {
        return afName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getEstimateGrade() {
        return estimateGrade;
    }

    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getGmName() {
        return gmName;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public Double getSo2() {
        return so2;
    }

    public Double getCo() {
        return co;
    }

    public Double getPm() {
        return pm;
    }

    public String getConfirmLevel() {
        return confirmLevel;
    }

    public String getConfirmExplain() {
        return confirmExplain;
    }

    @Override
    public String toString() {
        return "AqiFeedback{" +
                "afId=" + afId +
                ", afName='" + afName + '\'' +
                ", proviceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", address='" + address + '\'' +
                ", infomation='" + information + '\'' +
                ", estimateGrade='" + estimateGrade + '\'' +
                ", date='" + date + '\'' +
                ", state='" + state + '\'' +
                ", gmName='" + gmName + '\'' +
                ", confirmDate='" + confirmDate + '\'' +
                ", so2=" + so2 +
                ", co=" + co +
                ", pm=" + pm +
                ", confirmLevel='" + confirmLevel + '\'' +
                ", confirmExplain='" + confirmExplain + '\'' +
                '}';
    }
}
