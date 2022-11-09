package com.zzy.ucenter.service;

import com.zzy.ucenter.Vo.RegisterVo;
import com.zzy.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zzy
 * @since 2021-02-10
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    int countRegisterByDay(String day);
}
