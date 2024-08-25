package com.lin.backend_test.service.Impl;

import com.lin.backend_test.common.ServletUtils;
import com.lin.backend_test.entity.User;
import com.lin.backend_test.service.LoginService;
import com.lin.backend_test.service.UserRoleService;
import com.lin.backend_test.vo.ResponseVO;
import com.lin.backend_test.vo.StatusCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoginServiceImpl implements LoginService {
    private final UserRoleService userRoleService;

    public LoginServiceImpl(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    public  ResponseVO<Map<String, Object>> login(User user, String password) {
        if(!user.getPassword().equals(password)) {
            return ResponseVO.error(StatusCode.PASSWORD_NOT_RIGHT);
        }
        userRoleService.setUserRole(user);
        String roleNameList = user.getRoleList().stream().map(String::valueOf).collect(Collectors.joining(","));
        ServletUtils.setTokenData(user.getId(), user.getUserName(),roleNameList );
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("user", user);
        return ResponseVO.success(StatusCode.SUCCESS, returnMap);
    }
}
