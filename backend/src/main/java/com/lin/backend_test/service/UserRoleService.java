package com.lin.backend_test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.backend_test.entity.UserRole;

import java.util.List;
import java.util.Map;

public interface UserRoleService extends IService<UserRole> {

    IllegalArgumentException addUserRole(Integer userId, List<Integer> roleIdList, boolean deleteOldRole);

    List<Map<String, Object>>getUserIdsByRoleIds(List<Integer> roleIds);
}
