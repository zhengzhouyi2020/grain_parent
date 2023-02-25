package com.zzy.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.acl.entity.Permission;
import com.zzy.acl.entity.RolePermission;
import com.zzy.acl.entity.User;
import com.zzy.acl.helper.MemuHelper;
import com.zzy.acl.mapper.PermissionMapper;
import com.zzy.acl.service.PermissionService;
import com.zzy.acl.service.RolePermissionService;
import com.zzy.acl.service.UserService;
import com.zzy.acl.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.zzy.acl.helper.PermissionHelper.bulid;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2022-10-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;
    /**
     * 获取所以权限列表
     * @return
     */
    @Override
    public List<Permission> getAllpermission() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissions = baseMapper.selectList(wrapper);

        List<Permission> result = buildpermission(permissions);

        return result;
    }

    /**
     * 递归删除菜单
     * @param id
     */
    @Override
    public void removeid(String id) {
        //创建list集合，用来封装所以删除id值
        List<String> idlist = new ArrayList<>();
        this.selectpermissionbyId(id,idlist);
        //再把当前id放入idlist中
        idlist.add(id);
        baseMapper.deleteBatchIds(idlist);

    }

    /**
     * 给角色分配权限
     * @param roleId  角色id
     * @param permissionIds  菜单id
     */
    @Override
    public void saveRolePermission(String roleId, String[] permissionIds) {
        //创建list集合，用于封装
        List<RolePermission> rolePermissions = new ArrayList<>();

        for (String perId : permissionIds) {

            RolePermission rolePermission1 = new RolePermission();
            rolePermission1.setRoleId(roleId);
            rolePermission1.setPermissionId(perId);

            rolePermissions.add(rolePermission1);
        }

        rolePermissionService.saveBatch(rolePermissions);
    }

    /**
     * 根据用户id查询列表
     * @param id
     * @return
     */
    @Override
    public List<String> selectPermissionByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionvalueByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionvalueByUserId(userId);
        }

        List<Permission> permissionList = bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    /**
     * 根据角色id获取权限
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = bulid(allPermissionList);
        return permissionList;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     * 根据当前id，查询子菜单id，放到集合中
     * @param id
     * @param idlist
     */
    private void selectpermissionbyId(String id, List<String> idlist) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> childList = baseMapper.selectList(wrapper);

        childList.stream().forEach(item -> {
            idlist.add(item.getId());
            //递归查询
            this.selectpermissionbyId(item.getId(),idlist);
        });
    }

    /**
     * 封装permission,找到顶级菜单
     * @param permissions
     * @return
     */
    private List<Permission> buildpermission(List<Permission> permissions) {
        //最终的子菜单集合
        ArrayList<Permission> result = new ArrayList<>();

        for (Permission permission : permissions) {
            if ("0".equals(permission.getPid())){
                permission.setLevel(1);
                result.add(selectchildren(permission,permissions));
            }
        }
        return result;
    }

    /**
     * 查询子菜单  查询递归
     * @param permission
     * @param permissions
     * @return
     */
    private Permission selectchildren(Permission permission, List<Permission> permissions) {
        //向一层菜单放二层
        permission.setChildren(new ArrayList<>());

        //判断二级菜单
        for (Permission item : permissions) {
            if (permission.getId().equals(item.getPid())){
                int level = permission.getLevel()+1;
                item.setLevel(level);

                if (permission.getChildren() == null){
                    permission.setChildren(new ArrayList<Permission>());
                }
                //把查询的二级菜单放到父菜单里
                permission.getChildren().add(selectchildren(item,permissions));
            }
        }

        return permission;

    }
}
