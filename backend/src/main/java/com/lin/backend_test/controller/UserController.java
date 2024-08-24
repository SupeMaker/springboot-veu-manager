package com.lin.backend_test.controller;

import com.lin.backend_test.entity.User;
import com.lin.backend_test.service.UserService;
import com.lin.backend_test.vo.ResponseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "获取全部用户信息")
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
        return ResponseVO.success(userList);
    }



}
