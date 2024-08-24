package com.lin.backend_test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.backend_test.entity.Permission;
import com.lin.backend_test.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    @Select("SELECT p.* FROM permission p LEFT JOIN role_permission rp ON p.id=rp.permission_id WHERE rp.role_id = #{roleId}")
    List<Permission> getPermissionsByRoleId(Integer roleId);

}
