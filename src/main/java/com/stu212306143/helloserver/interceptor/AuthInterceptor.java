package com.stu212306143.helloserver.interceptor;

import com.stu212306143.helloserver.common.Result;
import com.stu212306143.helloserver.common.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取本次请求的HTTP动词和具体路径
        String method = request.getMethod();
        String uri = request.getRequestURI();

        // 2. 手写细粒度放行规则
        // 规则A：POST请求 + 路径精确等于/api/users → 放行（允许注册）
        boolean isCreateUser = "POST".equalsIgnoreCase(method) && "/api/users".equals(uri);
        // 规则B：GET请求 + 路径以/api/users/开头 → 放行（允许查询用户）
        boolean isGetUser = "GET".equalsIgnoreCase(method) && uri.startsWith("/api/users/");

        // 只要满足上述任一合法公开规则，直接放行，无需查验Token
        if (isCreateUser || isGetUser) {
            return true;
        }

        // 3. 针对DELETE、PUT等敏感操作，执行严格的Token校验
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            // 构造带非法操作提示的错误JSON
            String errorJson = objectMapper.writeValueAsString(
                    Result.error(401, "非法操作:敏感动作[" + method + "]必须携带有效令牌")
            );
            response.getWriter().write(errorJson);
            return false;
        }

        // 令牌校验通过，放行
        return true;
    }
}