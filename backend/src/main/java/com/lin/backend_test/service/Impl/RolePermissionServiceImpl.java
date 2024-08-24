package com.lin.backend_test.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.backend_test.entity.Permission;
import com.lin.backend_test.entity.RolePermission;
import com.lin.backend_test.mapper.RolePermissionMapper;
import com.lin.backend_test.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private final RolePermissionMapper rolePermissionMapper;

    @Autowired
    public RolePermissionServiceImpl(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public List<Permission> getPermissionsByRoleId(Integer roleId) {
        return rolePermissionMapper.getPermissionsByRoleId(roleId);
    }
}
