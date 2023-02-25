package com.zzy.acl.service.impl;

import com.zzy.acl.entity.User;
import com.zzy.acl.service.PermissionService;
import com.zzy.acl.service.UserService;
import com.zzy.security.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:zzy
 * @create: 2022-10-13 17:13
 * @Description:
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名查数据库
     * @param
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        com.zzy.security.entity.User curuser = new com.zzy.security.entity.User();
        BeanUtils.copyProperties(user,curuser);

        //根据用户查询用户权限列表
        List<String> permissions =  permissionService.selectPermissionByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curuser);
        securityUser.setPermissionValueList(permissions);

        return securityUser;
    }
}
