package com.zzy.acl.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzy.acl.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author zzy
 * @since 2022-10-12
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取所以权限列表
     * @return
     */
    List<Permission> getAllpermission();

    /**
     * 删除菜单
     * @param id
     */
    void removeid(String id);

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionId
     */
    void saveRolePermission(String roleId, String[] permissionId);

    /**
     * 根据id查询用户权限列表
     * @param id
     * @return
     */
    List<String> selectPermissionByUserId(String id);

    List<JSONObject> selectPermissionvalueByUserId(String id);


    //更具角色id获取菜单
    List<Permission> selectAllMenu(String roleId);
}
