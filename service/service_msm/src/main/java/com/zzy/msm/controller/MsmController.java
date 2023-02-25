package com.zzy.msm.controller;

import com.zzy.msm.service.MsmService;
import com.zzy.msm.utils.RandomUtils;
import com.zzy.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description controller层
 * @Author Zzy
 * @Date 2021/2/9
 */
@RestController
@RequestMapping("/msm/message")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String>  redisTemplate;

    // 发送短信验证码到手机号
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone){

        //先从redis获取验证码，如果获取到直接返回
        String code=redisTemplate.opsForValue().get(phone);
        if(StringUtils.isNotBlank(code)){
            return R.ok();
        }
        // 如果获取不到。直接进行阿里云阿发送
        //生成四位随机值，传给阿里云进行发送
        code= RandomUtils.getFourBitRandom();
        Map<String,Object> params=new HashMap<>();
        params.put("code",code);
        //调用service中的发送短信的方法
        boolean success=msmService.send(params,phone);
        if(success){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error("code不能为空!");
    }
}
