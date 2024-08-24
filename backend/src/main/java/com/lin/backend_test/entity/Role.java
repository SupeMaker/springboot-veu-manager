package com.lin.backend_test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private Date createTime;

    private Date updateTime;

    // 状态 0:删除，1：启用
    private Integer status;

    public static  class Status {
        public static final Integer DELETE = 0;
        public static final Integer ENABLE = 1;
    }
}
