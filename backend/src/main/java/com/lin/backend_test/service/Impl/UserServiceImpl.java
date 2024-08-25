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
    public List<User> getUserList(String content, String minCreateTime, String maxCreateTime, Integer pageNum, Integer pageSize, String orderMethod, String orderby) {
        Integer startPageNum = (pageNum - 1) * pageSize;
        return userMapper.getUserList(content, minCreateTime, maxCreateTime, startPageNum, pageSize, orderMethod, orderby);
    }

    @Override
    public Integer getUserTotalNum(String content, String minCreateTime, String maxCreateTime) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status", User.Status.DELETE);
        if (null != content) {
            queryWrapper.like("user_name", content);
        }
        if (null != maxCreateTime) {
            queryWrapper.gt("create_time", maxCreateTime);
        }
        if (null != maxCreateTime) {
            queryWrapper.lt("create_time", maxCreateTime);
        }
        return Math.toIntExact(userMapper.selectCount(queryWrapper));
    }

    @Override
    public boolean addUser(User user) {
        if(userMapper.insert(user) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }


    @Override
    public boolean deleteUserByIds(List<Integer> userIds) {
        if (userMapper.deleteBatchIds(userIds) > 0) return true;
        return false;
    }
}
