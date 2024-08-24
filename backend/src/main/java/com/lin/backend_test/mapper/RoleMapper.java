package com.lin.backend_test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.backend_test.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
