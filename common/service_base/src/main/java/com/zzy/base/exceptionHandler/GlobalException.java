package com.zzy.base.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 自定义异常类
 * @Author Zzy
 * @Date 2021/1/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalException extends RuntimeException{

    private Integer code; //状态码
    private String msg; //异常信息

    //将错误信息放到message中
    @Override
    public String getMessage(){
        return msg;
    }
}
