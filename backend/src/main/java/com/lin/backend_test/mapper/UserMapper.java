package com.lin.backend_test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.backend_test.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    String  userSearchWhereSql = "`status` != 2" +
            "<if test='minCreateTime != null'> AND `create_time` &gt;= #{minCreateTime} </if>" +
            "<if test='maxCreateTime != null'> AND `create_time` &lt;= #{maxCreateTime} </if>" +
            "<if test='content != null'> AND `user_name` LIKE '%${content}%' </if>";
//            "<if test='content != null'> AND `user_name` LIKE #{content} </if>";

    @Select("<script>" +
            "SELECT * FROM user" +
            "<where>"+ userSearchWhereSql + "</where>" +
            "order by #{orderby} #{orderMethod} limit #{startPageNum}, #{pageSize}" +
            "</script>")
    List<User> getUserList(String content, String minCreateTime, String maxCreateTime, Integer startPageNum, Integer pageSize, String orderMethod, String orderby);
}
