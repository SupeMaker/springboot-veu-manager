package com.lin.backend_test.service;

import com.lin.backend_test.entity.User;
import com.lin.backend_test.vo.ResponseVO;

import java.util.Map;

public interface LoginService {
    public  ResponseVO<Map<String, Object>> login(User user, String password);
}
