package com.wxj.common.enums;

public enum ReserveEnum {
    OK("审核通过"),
    NO("审核不通过"),
    DONE("已结束");

    private String desc;

    ReserveEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
