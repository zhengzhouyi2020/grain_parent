package com.zzy.acl.controller;



import com.zzy.acl.entity.Permission;
import com.zzy.acl.service.PermissionService;
import com.zzy.utils.R;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author zzy
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @ApiOperation(value = "查询所有权限列表")
    public R indexAllPermission(){
        List<Permission> list =  permissionService.getAllpermission();
        return R.ok().data("list",list);
    }

    @DeleteMapping("/remove/{id}")
    @ApiOperation(value = "递归删除菜单")
    public R remove(@PathVariable String id){
        permissionService.removeid(id);
        return R.ok();
    }

    @PostMapping("/doAssign")
    @ApiModelProperty(value = "给角色分配权限")
    public R doAssign (String roleId,String[] permissionId){
        permissionService.saveRolePermission(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }


}

