package com.lin.backend_test.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.backend_test.entity.Permission;
import com.lin.backend_test.mapper.PermissionMapper;
import com.lin.backend_test.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl  extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private final PermissionMapper permissionMapper;

    @Autowired
    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
}
