package com.zzy.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.acl.entity.User;
import com.zzy.acl.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zzy
 * @since 2022-10-12
 */
public interface UserService extends IService<User> {

    User selectByUsername(String username);
}
