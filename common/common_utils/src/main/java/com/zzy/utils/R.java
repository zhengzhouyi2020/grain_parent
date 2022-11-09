package com.zzy.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 统一返回数据格式
 * @Author Zzy
 * @Date 2021/1/23
 * 使用this可以方便链式编程
 */
@Data
public class R {

    @ApiModelProperty("是否成功")
    private Boolean success;

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回消息")
    private String message;

    @ApiModelProperty("返回数据")
    private Map<String,Object> data=new HashMap<>();

    private R(){};

    public static R ok(){
        R result=new R();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功");
        result.setSuccess(true);
        return result;
    }

    public static R error(){
        R result=new R();
        result.setCode(ResultCode.ERROR);
        result.setMessage("失败");
        result.setSuccess(false);
        return result;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key , Object value){
        this.data.put (key , value);
        return this;
    }

    public R data(Map <String, Object> map){
        this.setData(map );
        return this;
    }

}
