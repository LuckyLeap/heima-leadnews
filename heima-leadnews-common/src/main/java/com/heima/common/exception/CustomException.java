package com.heima.common.exception;

import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

}