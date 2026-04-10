package com.stu212306143.helloserver.dto;

import lombok.Data;

// 专门接收前端传来的用户名+密码，不暴露数据库实体
@Data
public class UserDTO {
    private String username;
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
