package com.zzy.ucenter.mapper;

import com.zzy.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2021-02-10
 */
public interface MemberMapper extends BaseMapper<Member> {

    int selectRegisterCount(String day);
}
