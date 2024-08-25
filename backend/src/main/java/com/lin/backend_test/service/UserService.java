package com.lin.backend_test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.backend_test.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    List<Map<String, Object>> getUserRoleAndPermissionsByUserId(List<Integer> userIds);

    boolean existsByUserName(String userName);

    public List<User> getUserList(String content, String minCreateTime, String maxCreateTime, Integer pageNum, Integer pageSize, String orderMethod, String orderby);

    public Integer getUserTotalNum(String content, String minCreateTime, String maxCreateTime);

    public boolean addUser(User user);

    public boolean updateUser(User user);

    public boolean deleteUserByIds(List<Integer> userId);

}
