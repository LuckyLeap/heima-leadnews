package com.heima.model.common.dtos;

import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通用的结果返回类
 * @param <T>
 */
@Setter
@Getter
public class ResponseResult<T> implements Serializable {

    private String host;

    private Integer code;

    private String errorMessage;

    private T data;

    public ResponseResult() {
        this.code = 200;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.errorMessage = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
    }

    public static ResponseResult<Object> errorResult(int code, String msg) {
        ResponseResult<Object> result = new ResponseResult<>();
        return result.error(code, msg);
    }

    public static ResponseResult<Object> okResult(int code, String msg) {
        ResponseResult<Object> result = new ResponseResult<>();
        return result.ok(code, null, msg);
    }

    public static ResponseResult<Object> okResult(Object data) {
        ResponseResult<Object> result = new ResponseResult<>();
        result.setCode(AppHttpCodeEnum.SUCCESS.getCode());
        result.setErrorMessage(AppHttpCodeEnum.SUCCESS.getErrorMessage());
        result.setData(data);
        return result;
    }

    public static ResponseResult<Object> errorResult(AppHttpCodeEnum enums){
        return setAppHttpCodeEnum(enums,enums.getErrorMessage());
    }

    public static ResponseResult<Object> errorResult(AppHttpCodeEnum enums, String errorMessage){
        return setAppHttpCodeEnum(enums,errorMessage);
    }

    public static ResponseResult<Object> setAppHttpCodeEnum(AppHttpCodeEnum enums){
        return okResult(enums.getCode(),enums.getErrorMessage());
    }

    private static ResponseResult<Object> setAppHttpCodeEnum(AppHttpCodeEnum enums, String errorMessage){
        return okResult(enums.getCode(),errorMessage);
    }

    public ResponseResult<T> error(Integer code, String msg) {
        this.code = code;
        this.errorMessage = msg;
        return this;
    }

    public ResponseResult<T> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<T> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.errorMessage = msg;
        return this;
    }

    public ResponseResult<T> ok(T data) {
        this.data = data;
        return this;
    }
}