package com.lin.backend_test.controller;

import com.lin.backend_test.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@Tag(name = "控制器: 测试Swagger3", description = "描述Swagger3")
@RequestMapping("/hello")
public class helloController {

    @Operation(summary = "测试GET请求方法")
    @Parameters({@Parameter(name="name", description = "姓名", required = true),
                @Parameter(name="password", description = "密码", required = false)})
    @ApiResponses({
                @ApiResponse(responseCode = "200", description = "请求成功"),
                @ApiResponse(responseCode = "400", description = "请求参数没填好"),
                @ApiResponse(responseCode = "401", description = "没有权限"),
                @ApiResponse(responseCode = "403", description = "禁止访问"),
                @ApiResponse(responseCode = "404", description = "请求路径没有或页面跳转路径不对"),
    })
    @RequestMapping(value="/getTest", method = RequestMethod.GET)
    public String getTest(String name, String password) {
        System.out.println("name: " + name);
        System.out.println("phone: " + password);
        return "GET请求收到";
    }

    @GetMapping("/getHello")
    public String getHello() {
        return "hello world";
    }

    // 这里使用 form-data提交
    @RequestMapping(value = "/postTest1", method = RequestMethod.POST)
    public String postTest1(User user) {
        System.out.println(user);
        return "POST1请求收到";
    }

//    这里接收JSon数据
    @RequestMapping(value = "/postTest2", method = RequestMethod.POST)
    public String postTest2(@RequestBody  User user) {
        System.out.println(user);
        return "POST1请求收到";
    }


    @RequestMapping(value = "/postTest3", method = RequestMethod.POST)
    public boolean createOrUpdateMultipartFile(String folderPath, String fileName, MultipartFile multipartFile) {
        if(multipartFile == null) {
            return false;
        }
        File floder = new File(folderPath);
        if(!floder.exists()) {
            if (!floder.mkdirs()) {
                return false;
            }
        }

        File file = new File(floder.getAbsolutePath() + File.separator + fileName);
        try {
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
