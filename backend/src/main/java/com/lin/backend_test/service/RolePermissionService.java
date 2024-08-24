package com.lin.backend_test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.backend_test.entity.Permission;
import com.lin.backend_test.entity.RolePermission;

import java.util.List;

public interface RolePermissionService extends IService<RolePermission> {

    List<Permission> getPermissionsByRoleId(Integer roleId);
}
