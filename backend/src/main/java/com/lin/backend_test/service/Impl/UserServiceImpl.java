package com.lin.backend_test.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.backend_test.entity.User;
import com.lin.backend_test.mapper.UserMapper;
import com.lin.backend_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private  UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<Map<String, Object>> getUserRoleAndPermissionsByUserId(List<Integer> userIds) {
        return null;
    }

    @Override
    public boolean existsByUserName(String userName) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName.trim()).ne("status", 0));
        return user!=null;
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }
}
