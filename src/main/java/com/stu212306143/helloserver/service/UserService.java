package com.stu212306143.helloserver.service;

import com.stu212306143.helloserver.common.Result;
import com.stu212306143.helloserver.dto.UserDTO;

public interface UserService {
    // 注册接口
    Result<String> register(UserDTO userDTO);
    // 登录接口
    Result<String> login(UserDTO userDTO);
}
