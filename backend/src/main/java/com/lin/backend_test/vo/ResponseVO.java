package com.lin.backend_test.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {
    private final Integer status;

    private final String message;

    private final T data;

    public ResponseVO(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public static <T> ResponseVO<T> success() {
        return new ResponseVO<>(StatusCode.SUCCESS.getCode(), "success", null);
    }

    public static <T> ResponseVO<T> error() {
        return new ResponseVO<>(StatusCode.ERROR.getCode(), "error", null);
    }

    public static <T>ResponseVO success(T data) {
        return new ResponseVO(StatusCode.SUCCESS.getCode(), "success", data);
    }
}
