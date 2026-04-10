package com.stu212306143.helloserver.controller;

import com.stu212306143.helloserver.common.Result;
import com.stu212306143.helloserver.dto.UserDTO;
import com.stu212306143.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 依赖注入Service，必须加@Autowired
    @Autowired
    private UserService userService;

    // 1. 用户注册（POST /api/users）- 拦截器已放行
    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    // 2. 用户登录（POST /api/users/login）- 拦截器已放行
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    // 3. 查询用户信息（GET /api/users/{id}）- 拦截器已放行
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        return Result.success("查询成功,正在返回ID为" + id + "的用户信息");
    }

    // 4. 删除用户（DELETE /api/users/{id}）- 敏感接口，必须带Token才能访问
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable("id") Long id) {
        return Result.success("删除成功,已移除 ID 为 " + id + " 的用户");
    }
}