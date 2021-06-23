package com.atguigu.yygd.common.exception;

import com.atguigu.yygd.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.ok();
    }

    /**
     * 预约挂号异常处理，但需要手动抛出
     * @param e
     * @return
     */
    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result error(YyghException e){
        e.printStackTrace();
        return Result.ok();
    }
}
