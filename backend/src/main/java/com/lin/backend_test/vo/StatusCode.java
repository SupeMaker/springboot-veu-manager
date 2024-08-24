package com.lin.backend_test.vo;
import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS(200, "成功"),
    ERROR(400, "失败");

    Integer code;
    String desc;

    StatusCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
