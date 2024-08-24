package com.lin.backend_test.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.backend_test.entity.Role;
import com.lin.backend_test.mapper.RoleMapper;
import com.lin.backend_test.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public boolean existByName(String roleName) {
        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("name", roleName.trim()).eq("status", 1));
        return role != null;
    }
}
