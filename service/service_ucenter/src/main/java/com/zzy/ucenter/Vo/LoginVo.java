package com.zzy.ucenter.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description 封装登录类
 * @Author Zzy
 * @Date 2021/2/10
 */
@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginVo implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
