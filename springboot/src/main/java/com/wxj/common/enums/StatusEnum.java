package com.wxj.common.enums;

public enum StatusEnum {
    OK("空闲中"),
    NO("使用中"),
    ;

    private String desc;

    StatusEnum(String desc) {
        this.desc = desc;
    }
}
