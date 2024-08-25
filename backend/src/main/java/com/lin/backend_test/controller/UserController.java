package com.lin.backend_test.controller;

import com.lin.backend_test.common.ServletUtils;
import com.lin.backend_test.config.ApiConstants;
import com.lin.backend_test.entity.User;
import com.lin.backend_test.service.UserRoleService;
import com.lin.backend_test.service.UserService;
import com.lin.backend_test.vo.ResponseVO;
import com.lin.backend_test.vo.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(ApiConstants.API_PREFIX_MANAGER + "/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserRoleService userRoleService;

    @Autowired
    public UserController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Operation(summary = "获取用户列表")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "请求成功"),
            @ApiResponse(responseCode = "400", description = "请求参数没填好"),
            @ApiResponse(responseCode = "401", description = "没有权限"),
            @ApiResponse(responseCode = "403", description = "禁止访问"),
            @ApiResponse(responseCode = "404", description = "请求路径没有或页面跳转路径不对"),
    })
    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    public ResponseVO<List<User>> getUserList(){
        List<User> userList = userService.getUserList();
        return ResponseVO.success(StatusCode.SUCCESS, userList);
    }

    @Operation(summary = "获取用户自身信息")
    @GetMapping("/userInfo")
    public ResponseVO<User> getUserInfo() {
        User user = userService.getById(ServletUtils.getUserId());
        userRoleService.setUserRole(user);
        return ResponseVO.success(StatusCode.SUCCESS, user);
    }


}
