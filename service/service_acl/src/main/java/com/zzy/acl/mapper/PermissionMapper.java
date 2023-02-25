package com.zzy.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.acl.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2022-10-12
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据id查询拥有的菜单
     * @param id
     * @return
     */
    List<String> selectPermissionByUserId(String id);

    /**
     * 根据菜单查询所有的菜单列表
     * @return
     */
    List<String> selectAllPermissionValue();

    /**
     * 查询角色菜单
     * @param userId
     * @return
     */
    List<Permission> selectPermissionvalueByUserId(String userId);
}
