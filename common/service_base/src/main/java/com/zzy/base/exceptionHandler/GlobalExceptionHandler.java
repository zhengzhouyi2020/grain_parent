package com.zzy.base.exceptionHandler;

import com.zzy.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 统一异常处理
 * @Author Zzy
 * @Date 2021/1/23
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error("code不能为空!").message("执行全局异常处理！");
    }

    @ExceptionHandler(GlobalException.class)
    @ResponseBody//为了返回数据
    public R error(GlobalException e){
        e.printStackTrace();
        log .error(e.getMessage());  //错误日志输出到文件
        return R.error("code不能为空!").code(e.getCode()).message(e.getMsg());
    }
}
