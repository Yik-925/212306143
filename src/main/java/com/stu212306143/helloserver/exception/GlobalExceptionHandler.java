package com.stu212306143.helloserver.exception;


import com.stu212306143.helloserver.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获所有异常，返回统一错误格式
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        e.printStackTrace(); // 打印异常栈到控制台，方便排查
        return Result.error(500, "服务器异常：" + e.getMessage());
    }

    // 可以单独捕获特定异常，比如算术异常
    @ExceptionHandler(ArithmeticException.class)
    public Result<?> handleArithmeticException(ArithmeticException e) {
        return Result.error(500, "算术运算异常：" + e.getMessage());
    }
}
