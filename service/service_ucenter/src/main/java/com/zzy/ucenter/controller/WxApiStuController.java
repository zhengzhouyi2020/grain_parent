package com.zzy.ucenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.zzy.ucenter.entity.Member;
import com.zzy.ucenter.service.MemberService;
import com.zzy.ucenter.util.ConstantWxUtils;
import com.zzy.utils.JwtUtils;
import com.zzy.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import static com.zzy.ucenter.util.WeChatUtil.httpRequest;

/**
 * @Author: zzy
 * @Create: 2022-11-03 12:26
 * @Description: 微信小程序登录与退出
 */
@RestController //注意这里没有配置 @RestController
@RequestMapping("/educmsl/api/ucenterstu/wx")
public class WxApiStuController {

    @Autowired
    private MemberService memberService;


    @PostMapping("/login")
    public R wxlogin(@RequestBody Member user){

        String code = user.getCode();
        if (code == "" || "".equals(code)){
            return R.error("code不能为空!");
        }else {
            //微信接口服务,通过调用微信接口服务中jscode2session接口获取到openid和session_key
                String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + ConstantWxUtils.WX_OPEN_APP_ID + "&secret=" + ConstantWxUtils.WX_OPEN_APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
            String str = httpRequest(url, "GET", null); //调用工具类解密
            JSONObject jsonObject= JSONObject.parseObject(str);
            String openid = (String) jsonObject.get("openid");
            if(openid != null && !"".equals(openid)){
                //登录成功
                Member member = new Member();
                member.setNickname(user.getNickname());
                member.setOpenid(openid);
                member.setAvatar(user.getAvatar());
                member.setGmtCreate(new Date());
                member.setGmtModified(new Date());

                Member mymember = memberService.getOpenIdMember(openid);
                if (mymember == null){
                    //就是第一次登录,向数据库插入信息
                    //向数据库中插入一条记录
                    memberService.save(member);
                }else {
                    //更新数据
                    member.setId(mymember.getId());
                    memberService.updateById(member);

                }

                //使用jwt生成token，
                String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

                return R.ok().data("token",jwtToken);
            }


        }
        return R.error("登录失败");
    }
}
