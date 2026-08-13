package com.example.aispringboot.common;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局统一异常处理器
 * 拦截Controller抛出的各类异常，统一格式化返回给前端
 */
// @RestControllerAdvice：全局Controller增强注解
// 1. 作用：监听所有 @RestController 控制器抛出的异常
// 2. 结合@ExceptionHandler实现全局异常捕获
// 3. 自带ResponseBody，返回JSON格式数据
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获【参数校验异常】MethodArgumentNotValidException
     * 触发场景：接口入参DTO上加@Valid，字段校验不通过时抛出此异常
     * @param e 参数校验异常对象，封装所有字段错误信息
     * @return 统一标准响应结果
     */
    // @ExceptionHandler：指定当前方法捕获哪一类异常
    // 此处表示只捕获 MethodArgumentNotValidException 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handlerException(MethodArgumentNotValidException e) {
        // e.getBindingResult()：获取校验绑定结果对象，里面存放所有字段校验错误信息
        // .getFieldErrors()：获取所有字段错误集合 List<FieldError>
        String message = e.getBindingResult().getFieldErrors().stream()
                // stream()：JDK8流式操作，遍历所有字段错误
                // map(FieldError::getDefaultMessage)：取出每一条错误里定义的提示信息（message内容）
                .map(FieldError::getDefaultMessage)
                // Collectors.joining(", ")：把多条错误信息使用逗号拼接成一条字符串
                .collect(Collectors.joining(", "));

        // 封装错误返回对象：错误码、默认提示、拼接好的完整校验信息
        return Result.error(ResultCode.PARAM_ERROR.getCode(), ResultCode.PARAM_ERROR.getMsg(), message);
    }

}