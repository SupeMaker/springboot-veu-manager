package com.lin.backend_test.vo;
import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS(200, "成功"),
    ERROR(400, "失败"),
    USER_NOT_FOUND(1001, "用户不存在"),
    PASSWORD_NOT_RIGHT(1002, "密码不正确");

    Integer code;
    String desc;

    StatusCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
