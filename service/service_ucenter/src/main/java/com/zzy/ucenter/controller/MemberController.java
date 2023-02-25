package com.zzy.ucenter.controller;


import com.zzy.ucenter.Vo.RegisterVo;
import com.zzy.ucenter.entity.Member;
import com.zzy.ucenter.service.MemberService;
import com.zzy.utils.JwtUtils;
import com.zzy.utils.R;
import com.zzy.utils.orderVo.UcenterMemberOrder;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2021-02-10
 */
@RestController
@RequestMapping("/ucenter/member")
@Api(value = "UcenterMemberController", tags = "登录")
public class MemberController {
    @Autowired
    private MemberService memberService;

    //登录方法
    @PostMapping("/login")
    public R loginUser(@RequestBody Member member){
        // 用户登录需要封装手机号和密码
        // 返回登录的token令牌
        String token=memberService.login(member);
        return R.ok().data("token",token);

    }

    // 注册
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("/getUserInfo")
    public R getMemberInfo(HttpServletRequest request){
        // 调用JWTUtils,根据request偷心获取用户信息
        String id=JwtUtils.getMemberIdByJwtToken(request);
        // 根据用户id信息查询用户信息，包括头像
        Member member=memberService.getById(id);
        return R.ok().data("userInfo",member);
    }

    //根据用户id获取用户信息，返回用户信息对象
    @PostMapping("/getInfoUc/{id}")
    public UcenterMemberOrder getInfo(@PathVariable("id") String id){
        //根据用户id获取用户信息
        Member member=memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder=new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;

    }

    /**
     * 统计功能接口：根据日期查询某一天注册人数
     * @param day
     * @return
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        int count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }
}

