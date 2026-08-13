package com.example.aispringboot.common;

import com.example.aispringboot.dto.response.UserLoginResponseDTO;
import lombok.Data;

/**
 * 统一接口返回结果封装类（泛型响应体）
 * @param <T> 返回数据的类型
 */
@Data
public class Result<T> {
    /** 响应状态码：200成功，500默认失败，可自定义业务编码 */
    private String code;
    /** 响应提示信息 */
    private String msg;
    /** 返回业务数据 */
    private T data;

    /**
     * 响应成功【无返回数据】
     * 适用场景：新增、删除、修改等不需要返回数据的接口
     * @return Result
     */
    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    /**
     * 响应成功【携带返回数据】【重载方法】
     * 适用场景：查询接口，需要向前端返回查询结果
     * @param data 需要返回的业务数据
     * @return Result
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = ok();
        result.setData(data);
        return result;
    }

    /**
     * 默认响应失败【使用预设错误码与提示，不带自定义数据】
     * @return Result
     */
    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.ERROR.getCode());
        result.setMsg(ResultCode.ERROR.getMsg());
        return result;
    }

    /**
     * 自定义失败响应【自定义状态码、提示、附带数据】
     * 适用场景：业务异常、自定义业务错误码，可传递额外错误信息
     * @param code 自定义错误编码
     * @param msg 自定义错误提示
     * @param data 需要附带的额外数据
     * @return Result
     */
    public static <T> Result<T> error(String code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}