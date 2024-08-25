package com.lin.backend_test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "学生信息")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户名", example = "supoMaker")
    private String userName;

    @Schema(description = "真实姓名", example = "小明")
    private String trueName;

    @Schema(description = "密码", example = "123")
    private String password;

    @Schema(description = "头像路径", example = "D://photo//dilireba.jpeg")
    private String avatarPath;

    @Schema(description = "电话", example = "18869543692")
    private String phone;

    @Schema(description = "邮箱", example = "John@gmail.com")
    private String email;

    @Schema(description = "性别", example = "男")
    private String gender;

    @Schema(description = "地址", example = "可可西里东边茅草屋")
    private String address;

    // 状态 0:删除，1：启用
    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间", example = "2024-08-20 20:11:45")
    private Date createTime;

    @Schema(description = "更新时间", example = "2024-08-20 20:11:45")
    private Date updateTime;

    // 非数据库字段
    @TableField(exist = false)
    private List<Integer> roleList;

    // 非数据库字段
    @TableField(exist = false)
    private List<Integer> permissionList;

    public static  class Status {
        public static final Integer DELETE = 0;
        public static final Integer ENABLE = 1;
    }
}
