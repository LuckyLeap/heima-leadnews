package com.heima.common.exception;


import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice  //控制器增强类
@Slf4j
public class ExceptionCatch {

    /**
     * 处理不可控异常（所有未捕获的异常）
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult<Object> exception(Exception e) {
        String exceptionType = e.getClass().getSimpleName();
        log.error("[{}] 异常信息：{}", exceptionType, e.getMessage());
        log.debug("异常堆栈跟踪：", e); // debug级别记录详细堆栈
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }

    /**
     * 处理可控异常 - 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult<Object> exception(CustomException e) {
        log.error("错误码：{}, 异常信息：{}",
                e.getAppHttpCodeEnum().getCode(), e.getMessage());
        return ResponseResult.errorResult(e.getAppHttpCodeEnum());
    }

}
