package com.kang.kmall.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author Kangshitao
 * @date 2021年8月3日 下午6:26
 */
public enum GenderEnum {
    WOMAN(0,"女"),
    MAN(1,"男");

    @EnumValue
    private Integer code;
    private String value;

    GenderEnum() {
    }

    GenderEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
