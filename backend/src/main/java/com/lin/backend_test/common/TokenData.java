package com.lin.backend_test.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 封装Token数据
 */
public class TokenData {

    public Integer userId;

    public String userName;

    public String role;

    public TokenData(Integer userId, String userName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }

    public TokenData() {

    }

    public String toJSONString() {
        return "{\"userId\":" + userId + ",\"username\":" + getFormattedString(userName) +
                ",\"role\":" + getFormattedString(role) + "}";
    }

    private static String getFormattedString(String value) {
        return value == null ? null : "\"" + value + "\"";
    }

    private final static Gson gson = new GsonBuilder().create();

    public static TokenData formJSONString(String jsonString) {
        if(null == jsonString) return null;
        try {
//            return gson.fromJson(jsonString, TokenData.class);
            return new ObjectMapper().readValue(jsonString, TokenData.class);
        } catch (Exception e) {
            return null;
        }
    }
}
