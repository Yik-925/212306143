package com.stu212306143.helloserver.entity;

// MyBatis-Plus 核心注解（正确包名）
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
// Lombok 简化代码注解（可选）
import lombok.Data;

// 绑定数据库表名sys_user，必须和数据库里的表名一致
@TableName("sys_user")
@Data // Lombok注解，自动生成Getter/Setter/无参构造，不用就手动生成
public class User {
    // 主键自增
    @TableId(type = IdType.AUTO)
    private Long id;
    // 用户名（唯一）
    private String username;
    // 密码
    private String password;

    public User() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}