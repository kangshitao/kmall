package com.kang.kmall.enums;

/**
 * @author Kangshitao
 * @date 2021年8月2日 上午10:44
 */
public enum ResultEnum {
    STOCK_ERROR(1,"库存不足");
    private Integer code;
    private String  msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
