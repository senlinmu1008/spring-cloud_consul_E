/**
 * Copyright (C), 2015-2019
 */
package com.zxb.basic.domain;

/**
 *
 * @author zhaoxb
 * @create 2019-11-12 20:57
 */
public enum ErrorCode {
    SUCCESS("000000", "交易成功"),
    SYSTEM_ERROR("999999", "系统异常"),
    ;
    private String value;

    private String desc;

    ErrorCode(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }
}