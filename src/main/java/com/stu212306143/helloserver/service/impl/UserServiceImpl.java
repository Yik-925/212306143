package com.stu212306143.helloserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.stu212306143.helloserver.common.Result;
import com.stu212306143.helloserver.common.ResultCode;
import com.stu212306143.helloserver.dto.UserDTO;
import com.stu212306143.helloserver.entity.User;
import com.stu212306143.helloserver.mapper.UserMapper;
import com.stu212306143.helloserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // 注入 Mapper（替换原来的 Map）
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 1. 查询用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        if (dbUser != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }

        // 2. 组装 User 对象
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        // 3. 插入数据库
        userMapper.insert(user);
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        // 1. 根据用户名查库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userDTO.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        // 2. 用户不存在
        if (dbUser == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 3. 密码错误
        if (!dbUser.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        return Result.success("登录成功");
    }

    // ===================== 新增：根据ID查询用户（实验要求） =====================
    @Override
    public Result<String> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            // 方式1：用你已有的 ResultCode 枚举（推荐，统一返回码）
            return Result.error(ResultCode.USER_NOT_EXIST);

            // 方式2：如果想自定义消息，可以用带错误码+消息的重载方法
            // return Result.error(404, "用户不存在");
        }
        return Result.success(user.toString());
    }
    @Override
    public Result<String> deleteUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        userMapper.deleteById(id);
        return Result.success("删除成功，已移除 ID 为 " + id + " 的用户");
    }
}