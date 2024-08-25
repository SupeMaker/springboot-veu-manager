package com.lin.backend_test.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {


    /**
     * 获取响应对象
     * @return 响应对象
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != servletRequestAttributes) {
            return servletRequestAttributes.getResponse();
        }
        return null;
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }


    public static TokenData getUserInfo(HttpServletRequest request) {
        return TokenUtils.getAllInfoFromToken(request.getHeader("Authorization"));
    }

    /**
     * 获取请求头中的用户信息
     *
     * @return 用户信息
     */
    public static TokenData getUserInfo() {
        return getUserInfo(getRequest());
    }

    /**
     * 从请求头中获取用户id
     *
     * @return 用户id
     */
    public static Integer getUserId() {
        TokenData userData = getUserInfo();
        return userData == null ? null : userData.userId;
    }

    /**
     * 从请求头中获取用户名
     *
     * @return 用户名
     */
    public static String getUsername() {
        TokenData userData = getUserInfo();
        return userData == null ? null : userData.userName;
    }


    /**
     * 设置token数据
     *
     * @param userId      用户id
     * @param username    用户名
     * @param roles       角色
     */
    public static void setTokenData(Integer userId, String username, String roles) {
        String token = TokenUtils.sign(new TokenData(userId, username, roles));
        getResponse().setHeader("Authorization", token);
    }

    /**
     * 移除token
     */
    public static void removeToken() {
        getResponse().setHeader("remove-token", "1");
    }

}
