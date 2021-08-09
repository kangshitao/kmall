package com.kang.kmall.exception;

import com.kang.kmall.enums.ResultEnum;

/**
 * @author Kangshitao
 * @date 2021年8月2日 上午9:17
 */
public class KMallException extends RuntimeException{
    public KMallException(String error) {
        super(error);
    }
    public KMallException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
    }
}
