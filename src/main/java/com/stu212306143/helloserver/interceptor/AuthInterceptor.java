package com.stu212306143.helloserver.interceptor;

import com.stu212306143.helloserver.common.Result;
import com.stu212306143.helloserver.common.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
// 核心修正：jakarta 替换为 javax
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取本次请求的HTTP动词和具体路径
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 2. 细粒度放行规则
        // 规则A：POST请求 + 路径精确等于/api/users → 放行注册
        boolean isCreateUser = "POST".equalsIgnoreCase(method) && "/api/users".equals(uri);
        // 规则B：POST请求 + 路径精确等于/api/users/login → 放行登录
        boolean isLogin = "POST".equalsIgnoreCase(method) && "/api/users/login".equals(uri);
        // 规则C：GET请求 + 路径以/api/users/开头 → 放行查询
        boolean isGetUser = "GET".equalsIgnoreCase(method) && uri.startsWith("/api/users/");

        // 满足任一规则直接放行
        if (isCreateUser || isLogin || isGetUser) {
            return true;
        }

        // 3. 敏感操作Token校验
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            String errorJson = objectMapper.writeValueAsString(
                    Result.error(401, "非法操作:敏感动作[" + method + "]必须携带有效令牌")
            );
            response.getWriter().write(errorJson);
            return false;
        }

        return true;
    }
}