package com.attguigu.abcommon.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zzj
 * @date 2022/10/18
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.getStackTrace();
        return R.error().message("全局异常处理");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public R error(RuntimeException re){
        re.getStackTrace();
        return R.error().message("处理运行时异常");
    }

    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public R error(YyghException y){
        y.printStackTrace();
        return R.error().code(y.getCode()).message(y.getMes());
    }
}
