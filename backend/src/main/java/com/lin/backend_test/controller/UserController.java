package com.lin.backend_test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.backend_test.common.DataUtils;
import com.lin.backend_test.common.ServletUtils;
import com.lin.backend_test.config.ApiConstants;
import com.lin.backend_test.entity.User;
import com.lin.backend_test.service.UserRoleService;
import com.lin.backend_test.service.UserService;
import com.lin.backend_test.vo.ResponseVO;
import com.lin.backend_test.vo.StatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
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


    /**
     *
     * @param minCreateTime 最小时间
     * @param maxCreateTime 最大时间
     * @param pageNum 第几页
     * @param pageSize 每页大小
     * @param orderMethod 升序: ASC; 降序： DESC
     * @param orderby 排序的字段： create_time
     * @return
     */
    @Operation(summary = "获取用户列表")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "请求成功"),
            @ApiResponse(responseCode = "400", description = "请求参数没填好"),
            @ApiResponse(responseCode = "401", description = "没有权限"),
            @ApiResponse(responseCode = "403", description = "禁止访问"),
            @ApiResponse(responseCode = "404", description = "请求路径没有或页面跳转路径不对"),
    })
    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
    public ResponseVO<Map<String, Object>> getUserList(@RequestParam (value = "content", required = false)String content,
                                                        @RequestParam (value = "minCreateTime", required = false)String minCreateTime,
                                                       @RequestParam (value = "maxCreateTime", required = false)String maxCreateTime,
                                                       @RequestParam (value = "pageNum", required = false)Integer pageNum,
                                                       @RequestParam (value = "pageSize", required = false)Integer pageSize,
                                                       @RequestParam (value = "orderMethod", required = false)String orderMethod,
                                                       @RequestParam (value = "orderby", required = false)String orderby){
        if("".equals(content)) content=null;
        if (DataUtils.isTimeFormatInvalid(minCreateTime)) {
            minCreateTime = null;
        }
        if (DataUtils.isTimeFormatInvalid(maxCreateTime)) {
            maxCreateTime = null;
        }
        if (null == orderMethod) {
            orderMethod = "desc";
        }
        if (null == orderby) {
            orderby = "create_time";
        }
        List<User> userList = userService.getUserList(content, minCreateTime, maxCreateTime, pageNum, pageSize, orderMethod, orderby);
        Integer totalNum = userService.getUserTotalNum(content, minCreateTime, maxCreateTime);
        Map<String, Object> returnmap = new HashMap<>();
        returnmap.put("userList", userList);
        returnmap.put("total", totalNum);
        return ResponseVO.success(StatusCode.SUCCESS, returnmap);
    }



//    @Operation(summary = "获取用户列表")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "请求成功"),
//            @ApiResponse(responseCode = "400", description = "请求参数没填好"),
//            @ApiResponse(responseCode = "401", description = "没有权限"),
//            @ApiResponse(responseCode = "403", description = "禁止访问"),
//            @ApiResponse(responseCode = "404", description = "请求路径没有或页面跳转路径不对"),
//    })
//    @RequestMapping(value = "/getAllUser", method = RequestMethod.GET)
//    public ResponseVO<Map<String, Object>> searchUserInfo(
//                                                        @RequestParam (value = "content", required = false)String content,
//                                                        @RequestParam (value = "minCreateTime", required = false)String minCreateTime,
//                                                       @RequestParam (value = "maxCreateTime", required = false)String maxCreateTime,
//                                                       @RequestParam (value = "pageNum", required = false)Integer pageNum,
//                                                       @RequestParam (value = "pageSize", required = false)Integer pageSize,
//                                                       @RequestParam (value = "orderMethod", required = false)String orderMethod,
//                                                       @RequestParam (value = "orderby", required = false)String orderby){
//        if (DataUtils.isTimeFormatInvalid(minCreateTime)) {
//            minCreateTime = null;
//        }
//        if (DataUtils.isTimeFormatInvalid(maxCreateTime)) {
//            maxCreateTime = null;
//        }
//        if (null == orderMethod) {
//            orderMethod = "desc";
//        }
//        if (null == orderby) {
//            orderby = "create_time";
//        }
//        List<User> userList = userService.getUserList(content, minCreateTime, maxCreateTime, pageNum, pageSize, orderMethod, orderby);
//        Integer totalNum = userService.getUserTotalNum();
//        Map<String, Object> returnmap = new HashMap<>();
//        returnmap.put("userList", userList);
//        returnmap.put("total", totalNum);
//        return ResponseVO.success(StatusCode.SUCCESS, returnmap);
//    }
//


    @Operation(summary = "获取用户自身信息")
    @GetMapping("/userInfo")
    public ResponseVO<User> getUserInfo() {
        User user = userService.getById(ServletUtils.getUserId());
        userRoleService.setUserRole(user);
        return ResponseVO.success(StatusCode.SUCCESS, user);
    }

//    @Operation(summary = "添加用户")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "请求成功"),
//            @ApiResponse(responseCode = "400", description = "请求参数没填好"),
//            @ApiResponse(responseCode = "401", description = "没有权限"),
//            @ApiResponse(responseCode = "403", description = "禁止访问"),
//            @ApiResponse(responseCode = "404", description = "请求路径没有或页面跳转路径不对"),
//    })
//    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
//    public ResponseVO addUser(@RequestParam (value = "userName", required = false)String userName,
//                               @RequestParam (value = "trueName", required = false)String trueName,
//                               @RequestParam (value = "password", required = false)String password,
//                               @RequestParam (value = "email", required = false)String email,
//                               @RequestParam (value = "gender", required = false)String gender,
//                               @RequestParam (value = "address", required = false)String address,
//                               @RequestParam (value = "phone", required = false)String phone,
//                               @RequestParam (value = "roleIds", required = false)String roleIds){
//        Date date = new Date();
//        User user = new User();
//        user.setUserName(userName)
//                .setTrueName(trueName)
//                .setPassword(password)
//                .setEmail(email)
//                .setGender(gender)
//                .setAddress(address)
//                .setPhone(phone)
//                .setStatus(User.Status.ENABLE)
//                .setCreateTime(date)
//                .setUpdateTime(date);
//        if(userService.addUser(user)) {
//            return ResponseVO.success();
//        }
//        return ResponseVO.error();
//    }

    @Operation(summary = "添加用户")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "请求成功"),
            @ApiResponse(responseCode = "400", description = "请求参数没填好"),
            @ApiResponse(responseCode = "401", description = "没有权限"),
            @ApiResponse(responseCode = "403", description = "禁止访问"),
            @ApiResponse(responseCode = "404", description = "请求路径没有或页面跳转路径不对"),
    })
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseVO addUser(@RequestBody String userJson){
        Date date = new Date();
        User user = new User();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(userJson);
            String userName = DataUtils.formatString(String.valueOf(jsonNode.get("userName")));
            String trueName = DataUtils.formatString(String.valueOf(jsonNode.get("trueName")));
            String password = DataUtils.formatString(String.valueOf(jsonNode.get("password")));
            String email = DataUtils.formatString(String.valueOf(jsonNode.get("email")));
            String gender = DataUtils.formatString(String.valueOf(jsonNode.get("gender")));
            String address = DataUtils.formatString(String.valueOf(jsonNode.get("address")));
            String phone = DataUtils.formatString(String.valueOf(jsonNode.get("phone")));
            String roleIdStr = DataUtils.formatString(String.valueOf(jsonNode.get("roleList")));
            List<Integer> roleIdslist = new ArrayList<>();
            if (!"[]".equals(roleIdStr) && null!=(roleIdStr)) {
                roleIdslist=Arrays.stream(roleIdStr.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }
            if(null== gender || "".equals(gender) || "0".equals(gender)) {
                gender="男";
            } else {
                gender="女";
            }
            user.setUserName(userName)
                .setTrueName(trueName)
                .setPassword(password)
                .setEmail(email)
                .setGender(gender)
                .setAddress(address)
                .setPhone(phone)
                .setStatus(User.Status.ENABLE)
                .setRoleList(roleIdslist)
                .setCreateTime(date)
                .setUpdateTime(date);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(userService.addUser(user)) {
            return ResponseVO.success();
        }
        return ResponseVO.error();
    }


    @Operation(summary = "更新用户信息")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "请求成功"),
            @ApiResponse(responseCode = "400", description = "请求参数没填好"),
            @ApiResponse(responseCode = "401", description = "没有权限"),
            @ApiResponse(responseCode = "403", description = "禁止访问"),
            @ApiResponse(responseCode = "404", description = "请求路径没有或页面跳转路径不对"),
    })
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseVO updateUser(@RequestBody String userJson){
        Date date = new Date();
        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        try {
            JsonNode jsonNode = objectMapper.readTree(userJson);
            Integer userId = Integer.valueOf(String.valueOf(jsonNode.get("id")));
            String userName = DataUtils.formatString(String.valueOf(jsonNode.get("userName")));
            String trueName = DataUtils.formatString(String.valueOf(jsonNode.get("trueName")));
            String password = DataUtils.formatString(String.valueOf(jsonNode.get("password")));
            String email = DataUtils.formatString(String.valueOf(jsonNode.get("email")));
            String gender = DataUtils.formatString(String.valueOf(jsonNode.get("gender")));
            Integer status = Integer.parseInt(String.valueOf(jsonNode.get("status")));
            String address = DataUtils.formatString(String.valueOf(jsonNode.get("address")));
            String phone = DataUtils.formatString(String.valueOf(jsonNode.get("phone")));
            String roleIdStr = DataUtils.formatString(String.valueOf(jsonNode.get("roleList")));
            List<Integer> roleIdslist = new ArrayList<>();
            if (!"[]".equals(roleIdStr) && null!=(roleIdStr)) {
                roleIdslist=Arrays.stream(roleIdStr.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }
            if(null== gender || "".equals(gender) || "0".equals(gender)) {
                gender="男";
            } else {
                gender="女";
            }
            user = userService.getById(userId);
            user.setUserName(userName)
                    .setTrueName(trueName)
                    .setPassword(password)
                    .setEmail(email)
                    .setGender(gender)
                    .setAddress(address)
                    .setPhone(phone)
                    .setStatus(status)
                    .setRoleList(roleIdslist)
                    .setUpdateTime(date);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(userService.updateById(user)) {
            return ResponseVO.success();
        }
        return ResponseVO.error();
    }

    @Operation(summary = "删除用户")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "请求成功"),
            @ApiResponse(responseCode = "400", description = "请求参数没填好"),
            @ApiResponse(responseCode = "401", description = "没有权限"),
            @ApiResponse(responseCode = "403", description = "禁止访问"),
            @ApiResponse(responseCode = "404", description = "请求路径没有或页面跳转路径不对"),
    })
    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public ResponseVO deleteUser(@RequestParam (value = "userIds", required = false)List<Integer> userIds){
        if(userService.deleteUserByIds(userIds)){
            return ResponseVO.success();
        }
        return ResponseVO.error();
    }

    @PutMapping("/{id}/status")
    public ResponseVO<String> updateUserStatus(@PathVariable Integer id,
                                               @RequestParam (value = "status", required = true) Integer status) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseVO.error(StatusCode.USER_NOT_FOUND);
        }
        user.setStatus(status);
        userService.updateById(user);
        return ResponseVO.success();
    }

}
