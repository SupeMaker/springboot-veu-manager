package com.lin.backend_test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.backend_test.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    List<Map<String, Object>> getUserRoleAndPermissionsByUserId(List<Integer> userIds);

    boolean existsByUserName(String userName);

    public List<User> getUserList();
}
