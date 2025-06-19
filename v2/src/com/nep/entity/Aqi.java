package com.nep.entity;

import java.io.Serializable;

/*
@author  JIE
AQI标准指数
 */
public class Aqi implements Serializable {
    //AQI指数级别
    private String level;
    //AQI指数描述
    private String explain;
    //AQI对健康的影响
    private String impact;

    public Aqi() {
    }

    public Aqi(String level, String explain, String impact) {
        this.level = level;
        this.explain = explain;
        this.impact =  impact;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getLevel() {
        return level;
    }

    public String getExplain() {
        return explain;
    }

    public String getImpact() {
        return impact;
    }

    @Override
    public String toString() {
        return "Aqi{" +
                "level='" + level + '\'' +
                ", explain='" + explain + '\'' +
                ", impact='" + impact + '\'' +
                '}';
    }
}

