package com.lin.backend_test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.backend_test.entity.Role;

public interface RoleService extends IService<Role> {

    boolean existByName(String roleName );
}
