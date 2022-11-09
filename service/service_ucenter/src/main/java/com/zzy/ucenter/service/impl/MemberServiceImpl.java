package com.zzy.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzy.base.exceptionHandler.GlobalException;
import com.zzy.ucenter.Vo.RegisterVo;
import com.zzy.ucenter.entity.Member;
import com.zzy.ucenter.mapper.MemberMapper;
import com.zzy.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.utils.JwtUtils;
import com.zzy.utils.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2021-02-10
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 登录方法
    @Override
    public String login(Member member) {
//        获取手机和密码
        String mobile = member.getMobile();
        String password = MD5.encrypt(member.getPassword());
        //先做非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GlobalException(0, "登录失败");
        }
//        判断手机号
        QueryWrapper<Member> query = new QueryWrapper<>();
        query.eq("mobile", member.getMobile());
        Member mobileMember = baseMapper.selectOne(query);
        // 登录失败
        if (mobileMember == null) {
            throw new GlobalException(0, "登录失败");
        }
        // 判断密码
        //对密码进行加密，然后对数据库中及进行对比
//        md5进行加密
        System.out.println(password);
        if (!password.equals(mobileMember.getPassword())) {
            throw new GlobalException(0, "登录失败");
        }

        //判断是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new GlobalException(0, "登录失败");
        }

        //登陆成功，调用JWT工具类生成token
        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return token;

    }

    //编写注册方法
    @Override
    public void register(RegisterVo registerVo) {
//        获取用户信息
        String password = registerVo.getPassword();
        String nickName = registerVo.getNickname();
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();

        // 非空判断
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code)) {
            throw new GlobalException(0, "注册失败");
        }


        //判断验证码
        String mobileCode =  redisTemplate.opsForValue().get(mobile);
        if (!code.equals(mobileCode)){
            throw new GlobalException(0,"验证码错误!");
        }

        //查询数据库中的手机号是否重复
        int count=baseMapper.selectCount(new QueryWrapper<Member>().eq("mobile",mobile));
        if(count>0){
            throw new GlobalException(0,"手机号已存在");
        }

        // 添加注册信息到数据库
        Member member=new Member();
        member.setMobile(mobile);
        member.setNickname(nickName);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("https://zzy-edu.oss-cn-beijing.aliyuncs.com/avatar/20180414165909.jpg");
        baseMapper.insert(member);
    }

    @Override
    public int countRegisterByDay(String day) {
        return baseMapper.selectRegisterCount(day);
    }
}
