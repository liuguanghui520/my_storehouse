package com.nep.dto;

public class AqiLimitDto {
    private int intlevel;
    private String level;
    private String explain;
    private String color;

    public AqiLimitDto() {
    }

    public AqiLimitDto(int intlevel, String level, String explain, String color) {
        this.intlevel = intlevel;
        this.level = level;
        this.explain = explain;
        this.color = color;
    }

    public int getIntlevel() {
        return intlevel;
    }

    public void setIntlevel(int intlevel) {
        this.intlevel = intlevel;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "AqiLimitDto{" +
                "intlevel=" + intlevel +
                ", level='" + level + '\'' +
                ", explain='" + explain + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
