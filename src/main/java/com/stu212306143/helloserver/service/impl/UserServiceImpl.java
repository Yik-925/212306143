package com.stu212306143.helloserver.service.impl;

import com.stu212306143.helloserver.common.Result;
import com.stu212306143.helloserver.common.ResultCode;
import com.stu212306143.helloserver.dto.UserDTO;
import com.stu212306143.helloserver.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// 必须加@Service注解，把类交给Spring容器管理，否则Controller无法注入
@Service
public class UserServiceImpl implements UserService {

    // 用Map模拟数据库，key=用户名，value=密码
    private static final Map<String, String> userDb = new HashMap<>();

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 1. 校验用户名是否已存在
        if (userDb.containsKey(userDTO.getUsername())) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }
        // 2. 存入模拟数据库
        userDb.put(userDTO.getUsername(), userDTO.getPassword());
        // 3. 返回成功结果
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        // 1. 校验用户是否存在
        if (!userDb.containsKey(userDTO.getUsername())) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        // 2. 校验密码是否正确
        String dbPassword = userDb.get(userDTO.getUsername());
        if (!dbPassword.equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }
        // 3. 登录成功，生成Token返回给前端（模拟Token，后续可替换为JWT）
        String token = UUID.randomUUID().toString().replace("-", "");
        return Result.success(token);
    }
}