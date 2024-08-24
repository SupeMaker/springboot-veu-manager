package com.lin.backend_test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.backend_test.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT * FROM user;")
    public List<User> getUserList();
}
