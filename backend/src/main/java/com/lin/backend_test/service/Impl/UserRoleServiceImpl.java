package com.lin.backend_test.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.backend_test.entity.Role;
import com.lin.backend_test.entity.UserRole;
import com.lin.backend_test.mapper.RoleMapper;
import com.lin.backend_test.mapper.UserRoleMapper;
import com.lin.backend_test.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Autowired
    private final RoleMapper roleMapper;

    @Autowired
    private final UserRoleMapper userRoleMapper;

    @Autowired
    public UserRoleServiceImpl(RoleMapper roleMapper, UserRoleMapper userRoleMapper) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public IllegalArgumentException addUserRole(Integer userId, List<Integer> roleIdList, boolean deleteOldRole) {
        if(deleteOldRole) {
            removeByMap(new HashMap<String, Object>() {
                {
                    put("user_id", userId);
                }   
            });
        }
        if(roleIdList == null || roleIdList.size() == 0) {
            return null;
        }
        
        List<Role> roleList = roleMapper.selectBatchIds(roleIdList);
        HashSet<Object> roleIdSet = roleList.stream().collect(HashSet::new, (set, role) -> set.add(role.getId()), HashSet::addAll);
        if(roleIdList.stream().anyMatch(roleId -> !roleIdSet.contains(roleId))) {
            return new IllegalArgumentException("roleIdList中包含无效的roleId");
        }
        ArrayList<UserRole> userRoleList = new ArrayList<>();
        Date date = new Date();
        for (Integer roleId : roleIdList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId).setRoleId(roleId).setCreateTime(date);
            userRoleList.add(userRole);
        }
        saveBatch(userRoleList, userRoleList.size());
        return null;
    }

    @Override
    public List<Map<String, Object>> getUserIdsByRoleIds(List<Integer> roleIds) {
        return null;
    }
}
