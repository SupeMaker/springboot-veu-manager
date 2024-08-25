package com.lin.backend_test.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.backend_test.config.ApiConstants;
import com.lin.backend_test.entity.User;
import com.lin.backend_test.service.Impl.LoginServiceImpl;
import com.lin.backend_test.service.LoginService;
import com.lin.backend_test.service.UserRoleService;
import com.lin.backend_test.service.UserService;
import com.lin.backend_test.vo.ResponseVO;
import com.lin.backend_test.vo.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(ApiConstants.API_PREFIX_MANAGER + "/auth")
public class LoginController {

    @Autowired
    private final UserService userService;

    private final LoginService loginService;

    @Autowired
    private final UserRoleService userRoleService;


    @Autowired
    public LoginController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        loginService = new LoginServiceImpl(this.userRoleService);
    }

    @Operation(summary = "使用用户名和密码登录")
    @Parameters({@Parameter(name="userName", description = "姓名", required = true),
            @Parameter(name="password", description = "密码", required = true)})
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseVO<Map<String, Object>> login(String userName, String password) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>();
        userQueryWrapper.eq("user_name", userName).eq("status", User.Status.ENABLE);
        User user = userService.getOne(userQueryWrapper);
        if (null == user) {
            return ResponseVO.error(StatusCode.USER_NOT_FOUND);
        }
        return loginService.login(user, password);
    }
}
